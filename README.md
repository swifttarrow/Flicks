# Project 1 - *Flicks*

**Flicks** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **25** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **scroll through current movies** from the Movie Database API
* [X] Layout is optimized with the [ViewHolder](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView#improving-performance-with-the-viewholder-pattern) pattern.
* [X] For each movie displayed, user can see the following details:
  * [X] Title, Poster Image, Overview (Portrait mode)
  * [X] Title, Backdrop Image, Overview (Landscape mode)

The following **optional** features are implemented:

* [X] User can **pull-to-refresh** popular stream to get the latest movies.
* [X] Display a nice default [placeholder graphic](http://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#configuring-picasso) for each image during loading.
* [X] Improved the user interface through styling and coloring.

The following **bonus** features are implemented:

* [X] Allow user to view details of the movie including ratings and popularity within a separate activity or dialog fragment.
* [X] When viewing a popular movie (i.e. a movie voted for more than 5 stars) the video should show the full backdrop image as the layout.  Uses [Heterogenous ListViews](http://guides.codepath.com/android/Implementing-a-Heterogenous-ListView) or [Heterogenous RecyclerView](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) to show different layouts.
* [X] Allow video trailers to be played in full-screen using the YouTubePlayerView.
    * [X] Overlay a play icon for videos that can be played.
    * [X] More popular movies should start a separate activity that plays the video immediately.
    * [X] Less popular videos rely on the detail page should show ratings and a YouTube preview. (no preview, loads video directly)
* [X] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce boilerplate code.
* [X] Apply rounded corners for the poster or background images using [Picasso transformations](https://guides.codepath.com/android/Displaying-Images-with-the-Picasso-Library#other-transformations)
* [X] Replaced android-async-http network client with the popular [OkHttp](http://guides.codepath.com/android/Using-OkHttp) or [Volley](http://guides.codepath.com/android/Networking-with-the-Volley-Library) networking libraries.

The following **additional** features are implemented:

* [X] Added ellipses for overview text in portrait mode.
* [X] Changed coloring of stars to white.
* [X] Added a layout for landscape view of movie detail.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/H89pNJK.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [EzGif](http://ezgif.com/video-to-gif).
MP4 demo available on Github.

## Notes

Describe any challenges encountered while building the app.

* Spent some time configuring layouts so text/image were placed appropriately.
* Payload returns images of different sizes. Need to fix this on a subsequent iteration.
* Nesting callbacks was confusing. Needed the movieTrailer key to feed into the youtube's init callback loop.
* Tried to defensively code for null json keys using optJSONObject. It didn't do what I expected and I opted to leave it out for now.
* Couldn't get the showing of the thumbnail preview to work; suspected I need to know more about fragments.
* OkHttp threw me an error: "Only original thread that created a view hierarchy can touch its views." 
      * Worked around it via stackoverflow suggestion to use runOnUIThread().
* Butterknife didn't work with ViewHolders. Made a lot of attempts, ultimately just removed
  binding for that specific area.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [OkHttp](https://github.com/square/okhttp) - Efficient HTTP client.
- [Butterknife](http://jakewharton.github.io/butterknife/) - Field and method binding for Android views.

## License

    Copyright [2016] [Kevin Chang]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
