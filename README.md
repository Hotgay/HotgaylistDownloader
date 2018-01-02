# [Hotgaylist] downloader

This little app helps to download video from the website [Hotgaylist]. To run this app you need the jar file `.jar`, a configuration file `config.yaml` and a folder named `videos`.

## How to use it

You only need the `.jar` file.
1. Run it a first time.
   A configuration file will be created named `config.yaml`
2. Once the file is created, choose the video to download.
   This is done by modifying the `config.yaml` file (see below)
3. Run again the file.
   All the video present on the first page of the website selected will be downloaded.

It is important to target the correct page. Those pages are the one with the videos. They are similar looking as the main page : [HotGayList].

## Configuration file

The configuration file `config.yaml` contains different elements:

```
--- !autoloader.conf.Configuration
contentMap: !java.util.HashSet
  - !autoloader.conf.Reference
    activity: false
    name: HotGayList
    url: "http://hotgaylist.com"
defaultFolder: ./videos
filePath: config.yaml
```

- `contentMap`
  The `contentMap` element is the largest one. It contains all the page references. Their are stored in a format similar to the element unique element. To add new element, just copy the `HotGayList` one and modify the fields

  - `activity`

    The `activity` element indicates to the application if you want to download the videos linked to this page. You must write `true` to download them and `false` to not.


  - `name`

    The name of the page. This name is used to store the video in sub folder inside `videos`
  - `url`
    The `url` is the page url on [Hotgaylist]

- `defaultFolder`

  The `defaultFolder` attribute represent where to store the videos.

- `filePath`

  The `filePath` attribute represents the where the configuration file is stored. If the configuration file is not find, then a new one is created at : `./config.yaml`


[Hotgaylist]: http://hotgaylist.com
