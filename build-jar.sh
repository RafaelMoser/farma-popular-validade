#!/bin/sh
SRC=src
OUT=out
CLS=$OUT/classes
rm -rf "$OUT"
mkdir -p "$CLS"
echo "Compiling Java sources..."
javac -d "$CLS" "$SRC"/*.java || { echo "Compilation failed"; exit 1; }
echo "Creating jar..."
jar cfe "$OUT/farma-popular-validade.jar" App -C "$CLS" . || { echo "Jar creation failed"; exit 1; }
echo "Created $OUT/farma-popular-validade.jar"
