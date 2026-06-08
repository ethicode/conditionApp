#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
GRADLE_VERSION="8.14.4"
CACHE_DIR="${GRADLE_BOOTSTRAP_DIR:-$HOME/.cache/unibank-service-sgsn-demo-gradle}"
DISTRIBUTION_DIR="$CACHE_DIR/gradle-$GRADLE_VERSION"
ARCHIVE_NAME="gradle-$GRADLE_VERSION-bin.zip"
ARCHIVE_PATH="$CACHE_DIR/$ARCHIVE_NAME"
DISTRIBUTION_URL="https://services.gradle.org/distributions/$ARCHIVE_NAME"
GRADLE_BIN="$DISTRIBUTION_DIR/bin/gradle"

mkdir -p "$CACHE_DIR"

if [[ ! -x "$GRADLE_BIN" ]]; then
  if [[ ! -f "$ARCHIVE_PATH" ]]; then
    echo "Downloading Gradle $GRADLE_VERSION..."
    curl -fsSL "$DISTRIBUTION_URL" -o "$ARCHIVE_PATH"
  fi

  rm -rf "$DISTRIBUTION_DIR"
  unzip -q "$ARCHIVE_PATH" -d "$CACHE_DIR"
fi

cd "$ROOT_DIR"
exec "$GRADLE_BIN" "$@"
