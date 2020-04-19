# Usage
The below examples are made using Fish Shell. Create a copy of `.env.exmaple` named `.env` and fill in credentials as you please.

## Launch using docker-compose
The below command will build a docker image using Jib. Instantiate it along with a Postgresql container.

The idea is to mimic the Heroku environment locally for development or host it elsewhere.

```bash
./gradlew jibDockerBuild && docker-compose up app database
```

## Deploy to Heroku
`./gradlew jib && heroku container:release web -a fitlogapi`


## Login - get token
```bash
set access_token (echo '{
        "username": "email@gmail.com",
        "password": "password"
    }' | http :8080/login | jq -r '.access_token')
```

## Post exercise
```bash
echo '{
        "name": "Air Squat",
        "description": "Squats without weight"
    }' | http :8080/exercises "Authorization:Bearer $access_token"
```

## Get exercises
```bash
http :8080/exercises "Authorization:Bearer $access_token"
```
