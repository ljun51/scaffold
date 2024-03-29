# PostgreSQL

## docker
    docker run --name postgres-server -e POSTGRES_PASSWORD=postgres -d postgres:16.2

    docker run -it --rm --network some-network postgres psql -h test-postgres -U postgres

    docker run -d \
        --name postgres-server \
        -p 5432:5432 \
        -e POSTGRES_PASSWORD=postgres \
        -e PGDATA=/var/lib/postgresql/data/pgdata \
        -v ./db/postgresql:/var/lib/postgresql/data \
        postgres:16.2