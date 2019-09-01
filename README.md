# cron-downloader
Cron-based download tool

## How to build
```bash
# Docker-based build
docker-compose build
```

```bash
# Or if you have JDK 12+ downloaded
export JAVA_HOME=/path/to/jdk12
./mvnw clean install
```

## How to configure
```bash
# Step 1: create config file
echo '{
  "downloadDirectory": "/var/output",
  "downloaderList": [
    {
      "cron": "*/5 * * * * ?",
      "resourcePath": "http://worldtimeapi.org/api/timezone/Asia/Irkutsk.txt",
      "outputFileName": "TimeAtIrkutsk.txt"
    }
  ]
}' > config.json

# Step 2: set path to config file
export LOCAL_CONFIG_PATH=./config.json

# Step 3: set path to output directory
export LOCAL_OUT_PATH=./output
```

## How to run
```bash
docker-compose up
```