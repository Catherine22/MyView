MyView
===================
Dec. 16 2016  
Written by Catherine

## ViewPager
- [x] Scrollable
- [x] Set image on tab bar
- [MainActivity]

## Refreshable ListView
  - [x] Add headers
  - [x] Add footers (in this case, showing a ProgressBar) to load more items when it reaches the end.     

  > **Note:**
  > 1. Requesting split data from your server many times, and scroll to download next group of data to fill in ListView.
  > 2. Request fewer items at a time so that users don't download the whole data, they just download what the see.And your server queries database more efficient.
  > 3. Using Universal-Image-Loader, Volley and others libraries to load images.
  - [x] Pull to refresh with SwipeRefreshLayout
  - [RefreshableListFragment]


## Refreshable GridView (Use RecyclerView instead of GridView)
  - [x] Add headers
  - [x] Add footers (in this case, showing a ProgressBar) to load more items when it reaches the end.    
  - [x] Pull to refresh with SwipeRefreshLayout

## RecyclerView
  - [x] Both lists and grids
  - [x] Add headers (included touching event)
  - [x] Add footers (included touching event)
  - [x] Make headers and footers span all the columns in grids.
  - [x] Drag & drop
  - [x] Swipe to remove items
  - [x] Here is simple [RefreshableGridFragment] and [RecyclerViewAdapter]


#### Fresco Library
  - Load images
  - Using focusCrop scales images to focus point instead of the center so that you won't crop someone's face. Click to see more about [FocusCrop].


  [FocusCrop]:<http://frescolib.org/docs/scaling.html#FocusCrop>
   [MainActivity]:<https://github.com/Catherine22/MyView/blob/master/app/src/main/java/catherine/com/myview/MainActivity.java>  
   [RefreshableListFragment]:<https://github.com/Catherine22/MyView/blob/master/app/src/main/java/catherine/com/myview/RefreshableListFragment.java>  
   [RefreshableGridFragment]:<https://github.com/Catherine22/MyView/blob/master/app/src/main/java/catherine/com/myview/RefreshableGridFragment.java>  
   [RecyclerViewAdapter]:<https://github.com/Catherine22/MyView/blob/master/app/src/main/java/catherine/com/myview/adapters/RecyclerViewAdapter.java>  
   [RecyclerView/]:<https://github.com/Catherine22/MyView/blob/master/app/src/main/java/catherine/com/myview/view/recycler_view/>  
