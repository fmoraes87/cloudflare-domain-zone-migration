# Cloudflare Domain Copy Tool

This Java project provides functionality to copy domains between different Cloudflare accounts. It uses GraphQL to perform the domain synchronization.

## Table of Contents

- [Configuration](#configuration)
- [Running the Application in Development Mode](#running-the-application-in-development-mode)
- [Sync Domain](#sync-domain)
- [Building the Project](#building-the-project)
- [Troubleshooting](#troubleshooting)

## Configuration

1. Create a `.env` file in the root directory of the project.
2. Set the following environment variables with your Cloudflare credentials:

    ```plaintext
    FROM_CLOUDFLARE_GLOBAL_KEY=<your_from_cloudflare_global_key>
    FROM_CLOUDFLARE_EMAIL=<your_from_cloudflare_email>
    TO_CLOUDFLARE_GLOBAL_KEY=<your_to_cloudflare_global_key>
    TO_CLOUDFLARE_EMAIL=<your_to_cloudflare_email>
    ```

## Running the Application in Development Mode

You can run your application in development mode with live coding enabled using:

```shell
./mvnw compile quarkus:dev
```

## Sync Domain

To copy a domain from one Cloudflare account to another, use the following GraphQL mutation:

1. Open your preferred GraphQL client (e.g., Postman, Insomnia).
2. Send a POST request to the following URL:

    ```
    http://localhost:8080/graphql
    ```

3. Use the following GraphQL mutation:

    ```graphql
    mutation copyZone {
        copyZone(
            accountIdFrom: "ACCOUNT_FROM_ID",
            accountIdTo: "ACCOUNT_TO_ID",
            domain: "yourdomain.com"
        ) {
            id
        }
    }
    ```

Then, deploy the generated JAR file to your desired server.

## Troubleshooting

If you encounter issues, check the following:

- Ensure the environment variables in the `.env` file are correctly set.
- Verify your Cloudflare credentials and account IDs.
- Make sure the application is running on the specified port.

For further assistance, refer to the project documentation or open an issue on the project's repository.
