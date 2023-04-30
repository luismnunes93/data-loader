# Koerber Pharma Backend Challenge

## What this is

This is a solution for Koerber Pharma Backend Challenge.
I started from the base repo, I just decided to update to Spring Boot 3.0.6

## Requirements

 - Maven
 - Java 17
 - Docker

## Installation

Run the following commands in the command line:

    - docker build -t challenge-1.0.0.jar .
    - docker-compose up

## Loading Data

Change the left part of the volume `/home/lnunes/Projects/data-loader/data` and point it to the place where the data is on your machine

Note: expected files inside /data:
* zones.csv
* green_tripdata.csv
* yellow_tripdata.csv

```yaml
    volumes:
      - /home/lnunes/Projects/data-loader/data:/data
```
Import the data. Zones need to be imported before trips

    import zones: `curl -X POST http://localhost:8080/challenge/helper/import-zones`

    import green trip: `curl -X POST  http://localhost:8080/challenge/helper/import-green-trip`

    import yellow trip: `curl -X POST  http://localhost:8080/challenge/helper/import-yellow-trip`

Note: since green trip and yellow trip take longer to run (upload csv to database)
these methods are async. So even if the endpoint returns 200 OK, check service logs to verify is the batch job as finished.

## API Endpoints

### `top-zones`

This endpoint will return a list of the first 5 zones order by number of total pickups or the
number of total drop-offs

`GET /challenge/api/top-zones`

| Name  | Data Type | Required/Optional | Description                                               |
|:-----:|:---------:|:-----------------:|:----------------------------------------------------------|
| order |  string   |     required      | Order parameter. Accepted values `dropoffs` and `pickups` |

#### Example Request

    curl -X GET http://localhost:8080/challenge/api/top-zones?order=dropoffs

#### Response

```json
[
  {
    "zone": "East Harlem North",
    "pu_total": "23623",
    "do_total": "12783"
  },
  {
    "zone": "Central Harlem North",
    "pu_total": "12547",
    "do_total": "12546"
  },
  {
    "zone": "Astoria",
    "pu_total": "18822",
    "do_total": "12398"
  },
  {
    "zone": "Central Harlem",
    "pu_total": "21244",
    "do_total": "11229"
  },
  {
    "zone": "Jackson Heights",
    "pu_total": "10952",
    "do_total": "10979"
  }
]
```

### `zone-trips`

This endpoint will return the sum of the pickups and drop-offs in just one zone and one
date

`GET /challenge/api/zone-trips`

| Name | Data Type | Required/Optional | Description                 |
|:----:|:---------:|:-----------------:|:----------------------------|
| zone |    int    |     required      | zone id parameter           |
| date |  string   |     required      | date in format `yyyy-MM-dd` |

#### Example Request

    http://localhost:8080/challenge/api/zone-trips?zone=236&date=2018-01-12

#### Response

```json
{
    "zone": "Upper East Side North",
    "date": "2018-01-12",
    "pu": "89",
    "do": "411"
}
```

### zone-trips

`GET /challenge/api/zone-trips`

|   Name    |    Data Type     | Required/Optional | Description                                                                                                                   |
|:---------:|:----------------:|:-----------------:|:------------------------------------------------------------------------------------------------------------------------------|
|   page    |       int        |     required      | page number. note: page starts at 0                                                                                           |
|   size    |       int        |     required      | size of page                                                                                                                  |
|   sort    | string, asc/desc |     optional      | sort of the response. accepts a parameter followed by and asc (ascending) or desc (descending) divided by a `,` (e.g.: id,ASC |
| predicate |    Predicate     |     optional      | accepts any predicate that makes sense for this dataset  (trip)                                                               |


#### Example request

    http://localhost:8080/challenge/api/list-yellow?page=0&size=5&pickUp_id=236&sort=id,asc

#### Response

```json
{
  "content": [
    {
      "id": 1,
      "pickUpDate": "2018-01-01T00:18:50",
      "dropOffDate": "2018-01-01T00:24:39",
      "pickUp": {
        "id": 236,
        "borough": "Manhattan",
        "zone": "Upper East Side North",
        "serviceZone": "Yellow Zone"
      },
      "dropOff": {
        "id": 236,
        "borough": "Manhattan",
        "zone": "Upper East Side North",
        "serviceZone": "Yellow Zone"
      }
    },
    {
      "id": 67,
      "pickUpDate": "2018-01-01T00:27:07",
      "dropOffDate": "2018-01-01T00:41:47",
      "pickUp": {
        "id": 236,
        "borough": "Manhattan",
        "zone": "Upper East Side North",
        "serviceZone": "Yellow Zone"
      },
      "dropOff": {
        "id": 143,
        "borough": "Manhattan",
        "zone": "Lincoln Square West",
        "serviceZone": "Yellow Zone"
      }
    },
    {
      "id": 1602,
      "pickUpDate": "2018-01-01T00:53:59",
      "dropOffDate": "2018-01-01T01:07:57",
      "pickUp": {
        "id": 236,
        "borough": "Manhattan",
        "zone": "Upper East Side North",
        "serviceZone": "Yellow Zone"
      },
      "dropOff": {
        "id": 116,
        "borough": "Manhattan",
        "zone": "Hamilton Heights",
        "serviceZone": "Boro Zone"
      }
    },
    {
      "id": 2050,
      "pickUpDate": "2018-01-01T00:14:51",
      "dropOffDate": "2018-01-01T00:43:26",
      "pickUp": {
        "id": 236,
        "borough": "Manhattan",
        "zone": "Upper East Side North",
        "serviceZone": "Yellow Zone"
      },
      "dropOff": {
        "id": 48,
        "borough": "Manhattan",
        "zone": "Clinton East",
        "serviceZone": "Yellow Zone"
      }
    },
    {
      "id": 2067,
      "pickUpDate": "2018-01-01T00:33:52",
      "dropOffDate": "2018-01-01T00:39:32",
      "pickUp": {
        "id": 236,
        "borough": "Manhattan",
        "zone": "Upper East Side North",
        "serviceZone": "Yellow Zone"
      },
      "dropOff": {
        "id": 41,
        "borough": "Manhattan",
        "zone": "Central Harlem",
        "serviceZone": "Boro Zone"
      }
    }
  ],
  "pageable": {
    "sort": {
      "empty": false,
      "unsorted": false,
      "sorted": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 5,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 187,
  "totalElements": 933,
  "last": false,
  "size": 5,
  "number": 0,
  "sort": {
    "empty": false,
    "unsorted": false,
    "sorted": true
  },
  "numberOfElements": 5,
  "first": true,
  "empty": false
}
```

## How I build it


### Zones

Zones had headers so the mapping was super smooth just had to be careful to ignore the first line while 
reading/importing the csv.

### Trips had no header row

Since trips did not have a header row I had to go to  [original data website](https://www1.nyc.gov/site/tlc/about/tlc-trip-record-data.page)
to try and find which columns were ones I wanted to import. Even after reading both 
[green-data-dictionary](https://www.nyc.gov/assets/tlc/downloads/pdf/data_dictionary_trip_records_green.pdf), 
[yellow-data-dictionary](https://www.nyc.gov/assets/tlc/downloads/pdf/data_dictionary_trip_records_yellow.pdf) and the
trip data changes (csv) on the official website there as not 100%, but after a bit of "guessing" I was able to identify
the PickUpId and DropOfId.

### Model (Mapping from CSV to Database)

Zone id in the database is dictated by the Id on the csv and has all the attributes present on it.
```java
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Zone {

    @Id
    private Integer id;

    private String borough;
    private String zone;
    private String serviceZone;
}
```

Trip on the contrary has a `@GeneratedValue(strategy= GenerationType.IDENTITY)`
To have foreign keys to Zone I used `@ManyToOne(fetch = FetchType.LAZY, optional = false)` in both
PickUp and DropOff.

I don't use any cascade because I handle data independently, since it is imported from a CSV.

```java
@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime pickUpDate;
    private LocalDateTime dropOffDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Zone pickUp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Zone dropOff;
}
```

### Spring Batch

I started by simple using `opencsv` more specifically `CsvToBeanBuilder` to transform the csv into java objects (my classes)
and then save to the database. This proved to be super slow since even the green-trips was taking more than 1 minute to load.
And since yellow-trips was 10x the size, this proved to be a slow method. I tried changing from `opencsv` to 
`apache commons csv` but it proved to be in the same order magnitude in terms of data loading.

I though and searched about the matter and found `Spring Batch` to be a possible solution for my problem.

I implemented one batch job per each (zone, green-trip, yellow-trip) and it proved to be much faster than the prior alternative.

Each job has these steps:
* Reader: reads data from (csv) excluding columns and other mapping others to values

funny fact: excluding columns is pretty awesome, the proof is this commit: [magical-commit](https://github.com/luismnunes93/data-loader/commit/187ed447daa02150df12fe4f7dab3f27796b6bca)
. Before code was kinda cursed.

``` 
.includedFields(new Integer[] {1,2,5,6})
.names("pickUpDate", "dropOffDate", "pickUp", "dropOff"
```

* Processor: transform incoming data form reader into db ready data
```
final TripDbFaker transformedTrip = new TripDbFaker();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime pickUpDate = LocalDateTime.parse(trip.getPickUpDate(), formatter);
        LocalDateTime dropOffDate = LocalDateTime.parse(trip.getDropOffDate(), formatter);

        transformedTrip.setPickUpDate(pickUpDate);
        transformedTrip.setDropOffDate(dropOffDate);

        transformedTrip.setPickUpId(trip.getPickUpId());
        transformedTrip.setDropOffId(trip.getDropOffId());
```
* Writer: writes data from processor directly to database

```
return new JdbcBatchItemWriterBuilder<TripDbFaker>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO Trip (pick_up_date, drop_off_date, pick_up_id, drop_off_id) " +
                        "VALUES (:pickUpDate, :dropOffDate, :pickUpId, :dropOffId)")
                .dataSource(dataSource)
                .build();
```


From the example above it can be seen that `TripDbFaker` is used to insert the Trip into the database.
This is used to insert pickUpId and dropOffId as an Integer.

If we used the below lines with Trip (@Entity) setPickUp would have to be a `Zone` object
```
transformedTrip.setPickUpId(trip.getPickUpId());
transformedTrip.setDropOffId(trip.getDropOffId());
```

### Queries

I was not able to do achieve the result using simple jpa queries. So I had to use `@Query` as `nativeQuery = true`
Had some problems due to Postgres query. For example was using `AS do`, but since `do` is reserved, was getting syntax error,
it was hard to find since it worked nice in H2. Also had to use `AS something` on `FROM` inside a select.

#### getTopPickUpByLimit

Create a table FROM Trip GROUP BY pick_up_id   
JOIN with table crated FROM Trip GROUP BY drop_off_id  
INNER JOIN with Zone to get more information about Zone  
`ORDER BY pu_total`and add the LIMIT 5

#### getTopDropOffByLimit

Same as above just change the ORDER BY to `ORDER BY do_total DESC`

#### getSumOfPicksUpsAndDropOffsByZoneAndDate

Logic is similar to the two above. Just add WHERE clause to both help tables
`WHERE CAST(pick_up_date as date) >= :beginDate AND CAST(pick_up_date as date) <= :endDate AND pick_up_id = :zone` 
to filter by Date and PickUpId/DropOffId.

### Mapping from Native Query to Object

Decided to go for an interface to Map the nativeQuery to an object

```
public interface TopZoneTuple {

    String getZone();
    String getPu_total();
    String getDo_total();
}
```

### Mapping to Dtos

Mixture of Mapstruct to map from Trip (@Entity) to TripDto and some functions to map from 

`@JsonProperty` to map to desired json output format

    @JsonProperty("pu")
    private String pu_total;
    @JsonProperty("do")
    private String do_total;