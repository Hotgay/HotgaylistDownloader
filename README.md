# [Hotgaylist] downloader

This little app helps to download video from the website [Hotgaylist]

## The different elements

To run this app you need the jar file `.jar`, a configuration file `config.yaml` and a folder named `videos`.

## How to use it

You only need the `.jar` file. Other files are not required and are here to provide the source code and instructions.
1. Run it a first time.
   A configuration file will be created named `config.yaml`
2. Once the file is created, choose the video to download.
   This is done by modifying the `config.yaml` file (see below)
3. Run again the file.
   All the video present on the first page of the website selected will be downloaded.

## Configuration file

The configuration file `config.yaml` contains different elements:

### General structure of `config.yaml`
```
contentMap: 
  aWebsite:
    activity: false
    lastId: ""
    name: aName
    url: "http:xxxx"
defaultFolder: ./videos
filePath: config.yaml
websiteUrl: "http://hotgaylist.com"
```

- `contentMap`: The `contentMap` element is the largest one. It contains all the website references. Their are stored in a format similar to the element `aWebsite`.

  - `aWebsite`: The `aWebsite` represents the structure used to represent a website. This elements is divided in several other elements

    - `activity`: The `activity` element indicates to the application if you want to download the videos linked to this website. You must write `true` to download them and `false` to not.
    - `lastId`: No longer used
    - `name`: The name of the website. This name is used to store the video in sub folder inside `videos`
    - `url`: The `url` is the url of the website on [Hotgaylist]

- `defaultFolder`: The `defaultFolder` attribute represent where to store the videos.

- `filePath`: The `filePath` attribute represents the where the configuration file is stored. If the configuration file is not find, then a new one is created at : `./config.yaml`

- `websiteUrl`: The `websiteUrl` store the url of [Hotgaylist] website. Can be useful if it changes in the future.

[Hotgaylist]: http://hotgaylist.com
