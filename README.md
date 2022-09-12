
# Goplay Pretest ( TMDB API App )

A simple app to see Now Playing, Popular, Upcoming, Top Rated movies arround the world


## Tech Stack

- MVVM (Model View ViewModel)
- Clean Architecture, Here's my architecture diagram [Here](https://drive.google.com/file/d/1y9azri0VDsVBlC-cX8d601g8u4mlwc0j/view?usp=sharing)
- Repository Pattern
- Coroutine Flow
- Koin Dependency Injection
- Kotlin Dsl
- Jetpack Paging
- Coil Image Loader
- Retrofit

## How to use the app

- First Splashscreen will be opened
- And then , we will be navigated into HomeActivity. in this page we can se list of movies and movies that we add to favorite
- if we click the poster or info button, dialog detail will be shown
- and if we click see more in the section in home feeds, it will be open list paginated movies based on section type

## TMDB Api that we used

#### Get Now Playing Movie

```http
  GET movie/now_playing
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. TMDB Api Key   |
| `page` | `int` |  Page for response  |
| `language` | `string` | Language for response  |



#### Get Upcoming Movie

```http
  GET movie/upcoming
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. TMDB Api Key   |
| `page` | `int` |  Page for response  |
| `language` | `string` | Language for response  |


#### Get Popular Movie

```http
  GET movie/popular
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. TMDB Api Key   |
| `page` | `int` |  Page for response  |
| `language` | `string` | Language for response  |



#### Get Top Rated Movie

```http
  GET movie/top_rated
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `api_key` | `string` | **Required**. TMDB Api Key   |
| `page` | `int` |  Page for response  |
| `language` | `string` | Language for response  |

