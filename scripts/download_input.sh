#!/bin/bash

# Check if a day number and session token are provided
if [ -z "$1" ] || [ -z "$2" ]; then
  echo "Usage: $0 <day_number> <session_token>"
  exit 1
fi

DAY_NUMBER=$1
SESSION_TOKEN=$2
DAY_DIR="../src/test/resources/day${DAY_NUMBER}"
INPUT_FILE="${DAY_DIR}/input"

# Create the directory if it doesn't exist
if [ ! -d "$DAY_DIR" ]; then
  mkdir -p "$DAY_DIR"
fi

# Download the input file
URL="https://adventofcode.com/2024/day/${DAY_NUMBER}/input"
HTTP_STATUS=$(curl -s -w "%{http_code}" -H "Cookie: session=${SESSION_TOKEN}" -o "$INPUT_FILE.tmp" "$URL")

# Check if the download was successful
if [ "$HTTP_STATUS" -eq 200 ]; then
  mv "$INPUT_FILE.tmp" "$INPUT_FILE"
  echo "Input for day ${DAY_NUMBER} downloaded successfully to ${INPUT_FILE}."
else
  rm "$INPUT_FILE.tmp"
  echo "Failed to download input for day ${DAY_NUMBER}. HTTP status: $HTTP_STATUS"
  exit 1
fi