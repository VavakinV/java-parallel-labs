#!/bin/bash
MPJ_HOME="/C/MPJ/mpj-v0_44"   
MPJ_JAR="$MPJ_HOME/lib/mpj.jar"
CLASS_NAME="Main"

javac -cp ".:$MPJ_JAR" -d . src/$CLASS_NAME.java
if [ $? -ne 0 ]; then
    echo "Ошибка компиляции."
    exit 1
fi

rm -f base_results.csv

sizes=(100000 1000000 10000000)
procs=(1)

echo "=== Запуск тестов ==="
for p in "${procs[@]}"; do
    for n in "${sizes[@]}"; do
        echo "Процессы: $p | N: $n"
        mpjrun.sh -np $p $CLASS_NAME $n 
        if [ $? -ne 0 ]; then
            echo "Ошибка запуска: процессы=$p, N=$n"
        fi
    done
done

echo "Тесты завершены. Результаты сохранены в results.csv"