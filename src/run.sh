#!/bin/bash

cd "$(dirname "$0")"

javac -cp ".:../lib/ojdbc8.jar" *.java

if [ $? -eq 0 ]; then
    java -cp ".:../lib/ojdbc8.jar" Main
else
    echo "Compilation failed!"
    read -p "Press any key to continue..."
fi

read -p "Press any key to continue..."