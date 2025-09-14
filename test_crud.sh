#!/bin/bash

BASE_URL="http://localhost:8080/api/posts"

echo "=== 1️⃣ Получаем все посты (должно быть пусто) ==="
curl -s $BASE_URL
echo -e "\n"

echo "=== 2️⃣ Создаём новый пост ==="
POST_DATA='{"content":"Hello World"}'
RESPONSE=$(curl -s -X POST -H "Content-Type: application/json" -d "$POST_DATA" $BASE_URL)
ID=$(echo $RESPONSE | grep -oP '(?<="id":)\d+')
echo "Ответ сервера при создании: $RESPONSE"
echo "Создан пост с ID = $ID"
echo -e "\n"

echo "=== 3️⃣ Получаем все посты после создания ==="
curl -s $BASE_URL
echo -e "\n"

echo "=== 4️⃣ Получаем пост по ID ==="
curl -s $BASE_URL/$ID
echo -e "\n"

echo "=== 5️⃣ Обновляем пост ==="
UPDATE_DATA='{"content":"Updated Content"}'
RESPONSE=$(curl -s -X PUT -H "Content-Type: application/json" -d "$UPDATE_DATA" $BASE_URL/$ID)
echo "Ответ сервера при обновлении: $RESPONSE"
echo -e "\n"

echo "=== 6️⃣ Получаем пост после обновления ==="
curl -s $BASE_URL/$ID
echo -e "\n"

echo "=== 7️⃣ Удаляем пост ==="
curl -s -X DELETE $BASE_URL/$ID
echo "Пост с ID = $ID удалён"
echo -e "\n"

echo "=== 8️⃣ Получаем все посты после удаления (должно быть пусто) ==="
curl -s $BASE_URL
echo -e "\n"
