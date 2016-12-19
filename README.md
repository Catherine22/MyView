MyView
===================
Dec. 16 2016  
Written by Catherine

## ViewPager
- [x] Scrollable
- [x] Setting image on tab bar
- [MainActivity]

## Refreshable ListView
  - [x] Add a header
  - [x] Add a footer (in this case, showing a ProgressBar) to load more items when it reaches end.    

  > **Note:**
  > 1. Requesting data from server at a time, but splitting the data into some groups to fill in ListView.
  > 2. It also avoids OOM when you've got a lot of images to show, because it increases heap by the first group of items if you don't scroll the ListView.
  > 3. Using Universal-Image-Loader, Volley and others libraries to load images.
  - [x] Pull to refresh with SwipeRefreshLayout
  - [RefreshableListFragment]


## Refreshable GridView(Using RecyclerView instead of GridView)
  - [x] Add a header
  - [x] Add a footer (in this case, showing a ProgressBar) to load more items when it reaches end.
  - [x] Pull to refresh with SwipeRefreshLayout

## RecyclerView
  - [x] Both lists and grids
  - [x] Add a header (included touching event)
  - [x] Add a footer (included touching event)
  - [x] Drag & drop
  - [x] Swipe to remove items


## Fresco Library
  - Loading images
  - Using focusCrop scaling images to focus point instead of the center so that you won't crop someone's face. Click to see more about [FocusCrop].


   [MainActivity]:<https://github.com/Catherine22/MyView/blob/master/app/src/main/java/catherine/com/myview/MainActivity.java>  
   [RefreshableListFragment]:<https://github.com/Catherine22/MyView/blob/master/app/src/main/java/catherine/com/myview/RefreshableListFragment.java>  
   [FocusCrop]:<http://frescolib.org/docs/scaling.html#FocusCrop>
