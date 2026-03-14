#!/bin/bash

# Цвета для вывода
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo "--- Начинаем тест совместимости PostgreSQL с SELECTOS ---"

# 1. Тест установки
echo -n "1. Установка PostgreSQL... "
sudo apt update > /dev/null
sudo apt install -y postgresql > /dev/null 2>&1
if [ $? -eq 0 ]; then
    echo -e "${GREEN}PASS${NC}"
else
    echo -e "${RED}FAIL${NC}"
    exit 1
fi

# 2. Проверка работы службы
echo -n "2. Проверка статуса службы... "
if systemctl is-active --quiet postgresql; then
    echo -e "${GREEN}PASS${NC}"
else
    echo -e "${RED}FAIL${NC}"
fi

# 3. Проверка порта 5432
echo -n "3. Проверка порта 5432... "
if ss -tuln | grep -q ":5432"; then
    echo -e "${GREEN}PASS${NC}"
else
    echo -e "${RED}FAIL${NC}"
fi

# 4. Функциональный тест (Создание БД)
echo -n "4. Создание тестовой таблицы... "
cd /tmp
sudo -u postgres psql -c "CREATE DATABASE test_compat;" > /dev/null 2>&1
sudo -u postgres psql -d test_compat -c "CREATE TABLE test (id serial PRIMARY KEY, name VARCHAR(50));" > /dev/null 2>&1

if [ $? -eq 0 ]; then
    echo -e "${GREEN}PASS${NC}"
else
    echo -e "${RED}FAIL${NC}"
fi

echo "--- Тестирование завершено ---"
