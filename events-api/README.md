# events-api

Spring Boot 3 (Java 17) REST API connected to the MySQL/MariaDB database
`lekgoloe_lekgolo_events` on `169.239.218.76`.

## Run

```bash
export DB_USERNAME=your_user
export DB_PASSWORD=your_password
mvn spring-boot:run
```

## Endpoints (`comments` table)

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/comments` | List all comments |
| GET | `/api/comments/{id}` | Get one comment |
| GET | `/api/comments/post/{postId}` | Comments for a post |
| GET | `/api/comments/{id}/replies` | Replies to a comment |
| POST | `/api/comments` | Create (id is auto-generated) |
| PUT | `/api/comments/{id}` | Update the body |
| DELETE | `/api/comments/{id}` | Delete |

Example POST body:

```json
{ "postId": "…", "authorId": "…", "parentCommentId": null, "body": "Nice event!" }
```

## Notes

- Table name is assumed to be `comments`; change `@Table(name = ...)` in `Comment.java` if it differs.
- The MySQL server must allow remote connections from your IP (port 3306).
- `ddl-auto=none` so Hibernate never modifies your existing schema.
