## Config
Create .env file and set
```
FROM_CLOUDFLARE_GLOBAL_KEY = 
FROM_CLOUDFLARE_EMAIL = 
TO_CLOUDFLARE_GLOBAL_KEY = 
TO_CLOUDFLARE_EMAIL = 
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

# Sync Domain 

URL: http://localhost:8080/graphql

```
mutation copyZone{
	copyZone(
		accountIdFrom: "ACCOUNT_FROM_ID"
		accountIdTo: "ACCOUNT_FROM_TO"
		domain: "yourdomain.com"){id}
}

