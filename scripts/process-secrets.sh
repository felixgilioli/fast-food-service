#!/bin/bash

# Script dinâmico para processar secrets do Kubernetes
# Encontra automaticamente todas as variáveis ${VAR_NAME} e as substitui
# pelas variáveis de ambiente correspondentes

set -e

SECRETS_FILE="${1:-secrets.yaml}"
BACKUP_FILE="${SECRETS_FILE}.bak"

# Função para logging com cores
log() {
    local level="$1"
    local message="$2"
    local timestamp="[$(date '+%Y-%m-%d %H:%M:%S')]"

    case "$level" in
        "INFO")  echo -e "\033[0;32m${timestamp} INFO: ${message}\033[0m" ;;
        "WARN")  echo -e "\033[0;33m${timestamp} WARN: ${message}\033[0m" ;;
        "ERROR") echo -e "\033[0;31m${timestamp} ERROR: ${message}\033[0m" ;;
        *)       echo "${timestamp} ${message}" ;;
    esac
}

# Verificar se o arquivo existe
if [ ! -f "$SECRETS_FILE" ]; then
    log "ERROR" "Arquivo $SECRETS_FILE não encontrado!"
    exit 1
fi

log "INFO" "Processando arquivo: $SECRETS_FILE"

# Fazer backup
cp "$SECRETS_FILE" "$BACKUP_FILE"
log "INFO" "Backup criado: $BACKUP_FILE"

# Encontrar todas as variáveis ${VAR_NAME} no arquivo
variables=$(grep -oE '\$\{[A-Z_][A-Z0-9_]*\}' "$SECRETS_FILE" | sort -u || true)

if [ -z "$variables" ]; then
    log "WARN" "Nenhuma variável no formato \${VAR_NAME} encontrada"
    exit 0
fi

log "INFO" "Variáveis encontradas:"
echo "$variables" | sed 's/^/  - /'

# Processar cada variável
missing_vars=()
processed_count=0

for variable in $variables; do
    # Extrair o nome da variável removendo ${ e }
    env_var_name=$(echo "$variable" | sed 's/\${//g' | sed 's/}//g')

    log "INFO" "Processando: $variable -> $env_var_name"

    # Verificar se a variável de ambiente existe
    if [ -z "${!env_var_name:-}" ]; then
        log "ERROR" "Variável de ambiente '$env_var_name' não está definida!"
        missing_vars+=("$env_var_name")
        continue
    fi

    # Escapar caracteres especiais para sed
    escaped_value=$(printf '%s\n' "${!env_var_name}" | sed 's/[[\.*^$()+?{|]/\\&/g')

    # Escapar a variável para usar no sed (precisa escapar $ { })
    escaped_variable=$(echo "$variable" | sed 's/\$/\\$/g' | sed 's/{/\\{/g' | sed 's/}/\\}/g')

    # Substituir a variável pelo valor
    sed -i "s|${escaped_variable}|${escaped_value}|g" "$SECRETS_FILE"

    log "INFO" "✓ $variable substituído com sucesso"
    ((processed_count++))
done

# Verificar se houve variáveis não encontradas
if [ ${#missing_vars[@]} -gt 0 ]; then
    log "ERROR" "As seguintes variáveis de ambiente não estão definidas:"
    printf '  - %s\n' "${missing_vars[@]}"

    # Restaurar backup
    mv "$BACKUP_FILE" "$SECRETS_FILE"
    log "INFO" "Arquivo original restaurado devido aos erros"
    exit 1
fi

# Verificar se ainda existem variáveis não processadas
remaining=$(grep -oE '\$\{[A-Z_][A-Z0-9_]*\}' "$SECRETS_FILE" || true)
if [ -n "$remaining" ]; then
    log "WARN" "Variáveis não processadas encontradas:"
    echo "$remaining" | sed 's/^/  - /'
else
    log "INFO" "✓ Todas as variáveis foram processadas com sucesso!"
fi

log "INFO" "Resumo: $processed_count variáveis processadas"

# Remover backup se tudo deu certo
rm -f "$BACKUP_FILE"
log "INFO" "Processamento concluído!"