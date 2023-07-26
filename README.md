# Cron Controller

The `CronController` is a RESTful API controller that provides endpoints to manage cron jobs in the system. It allows users to create, start, stop, change configuration, and retrieve cron jobs.

## Endpoints

### Create a Cron Job

#### `POST /cron`

Creates a new cron job with the provided details.

Request body (`CreateCronDTO`):

```json
{
  "title": "Cron Job Title",
  "expression": "0 0 * * * ?" 
}
```

- `title` (String): The title of the cron job.
- `expression` (String): The cron expression defining the schedule for the cron job.

Response (`Cron`):

```json
{
  "id": 1,
  "title": "Cron Job Title",
  "expression": "0 0 * * * ?",
  "status": "STOP"
}
```

### Start a Cron Job

#### `PUT /cron/{cronId}/start`

Starts the specified cron job identified by `cronId`.

Path Parameters:
- `cronId` (Long): The ID of the cron job to start.

Response (String):

- If the cron job is successfully started: `"Cron started successfully"`
- If the cron job is already active: `"Cron is already active"`
- If the cron job is not found: `"Cron not found"`
- If there is an error starting the cron job: `"Failed to start cron"`

### Stop a Cron Job

#### `PUT /cron/{cronId}/stop`

Stops the specified cron job identified by `cronId`.

Path Parameters:
- `cronId` (Long): The ID of the cron job to stop.

Response (String):

- If the cron job is successfully stopped: `"Cron stopped successfully"`
- If the cron job is already inactive: `"Cron is already inactive"`
- If the cron job is not found: `"Cron not found"`
- If there is an error stopping the cron job: `"Failed to stop cron"`

### Change Cron Job Configuration

#### `PUT /cron/{cronId}`

Changes the configuration of the specified cron job identified by `cronId`.

Path Parameters:
- `cronId` (Long): The ID of the cron job to modify.

Request body (`CreateCronDTO`):

```json
{
  "expression": "0 0 12 * * ?" 
}
```

- `expression` (String): The new cron expression defining the updated schedule for the cron job.

Response (`Cron`):

```json
{
  "id": 1,
  "title": "Cron Job Title",
  "expression": "0 0 12 * * ?",
  "status": "STOP"
}
```

- If the cron job is successfully updated: Returns the updated cron job.
- If the cron job is not found: Returns a 404 Not Found response.

### Get Cron Job by ID

#### `GET /cron/{id}`

Retrieves the details of the cron job specified by `id`.

Path Parameters:
- `id` (Long): The ID of the cron job to retrieve.

Response (`Cron`):

```json
{
  "id": 1,
  "title": "Cron Job Title",
  "expression": "0 0 * * * ?",
  "status": "STOP"
}
```

- If the cron job is found: Returns the details of the cron job.
- If the cron job is not found: Returns a 404 Not Found response.

## Dependencies

- `CronService`: A service responsible for managing cron jobs in the application.
- `CronJobService`: A service that handles the Quartz cron job creation and scheduling.
- `Scheduler`: A Quartz Scheduler instance used for scheduling cron jobs.

Note: Make sure to properly configure and set up the Quartz scheduler in your application to use this controller effectively.

Please ensure that you have the necessary dependencies and configurations in your project to run this `CronController`. You can customize the endpoints or add error handling as per your application requirements.