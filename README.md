# TODO
* Create new google backend project

# Usage
The below examples are made using Fish Shell

## Login - get token
```bash
set access_token (echo '{
        "username": "email@gmail.com",
        "password": "password"
    }' | http :8080/login | jq -r '.access_token')
```

## Deploy to Heroku
`./gradlew jib && heroku container:release web -a fitlogapi`

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
