import 'dart:convert';

import 'package:assets_audio_player/assets_audio_player.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: Center(child: MyHomePage(title: 'Audio Stream')),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  void play(String url) async {
    // String url =
    //     'https://files.freemusicarchive.org/storage-freemusicarchive-org/music/Music_for_Video/springtide/Sounds_strange_weird_but_unmistakably_romantic_Vol1/springtide_-_03_-_We_Are_Heading_to_the_East.mp3';
    // String url =
    //     'http://localhost:8096/Items/3711d4f3013f9eb9899451a0f60a3a7c/Download?api_key=52eaa91a22634b8b85cb4d80637db28a';
    //url = 'https://www.dropbox.com/s/pj7yybx9ry9zbdl/ChanakyaNeeti_ep6.mp3';
    //String url =
    //"https://anmolthedeveloper.s3.ap-south-1.amazonaws.com/file1.mp3?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEFwaCXVzLWVhc3QtMSJGMEQCIGByN5PlKdUf2%2B2GdJXuakw%2B0QP%2BVMawTlw5XDVienewAiARBLeofD7uabK%2FVuz%2FIAhOsEsi5JJqqOAlIZFS591ggiq2AwjU%2F%2F%2F%2F%2F%2F%2F%2F%2F%2F8BEAAaDDEyMDMxNDM4MDcwOCIMazkJsK%2Fc8BIacoosKooDJEoFarbit1RmAdH8RrEEXNJXR2ldiNCNoW86J6cLVOwJdUaVow1BEbQl9xknBgAPlx1VluwPH%2B%2Fqn%2F%2FI6M8FigUT0l4uW%2BIwq9kJ%2FgBFkOwkBno78ruieea8dxwfQPt6rduUtswY7Bew5Qak7ecEZqSxaybb%2Fl7ocRr%2BwnaXMlFVIyXaJu0wAhirnDhom7DQV57EeNvgMniHaGN4KD2c9ljSW3zx9zu7IFbu8uumBrSEe1uNvIebg6zbJLpFPMJgKk60YSnZ7ROICOwBG8oCMuRX0BXrmGyDEzQR6%2B5vJrrg0khpUeu0%2FHtTIv8AkEn%2FKnls2t7l1k2qhVBsvlMPLoaErMEVRr6Iu4wzwoMd50y27wRIn3tYlBQaUOEnX2wyWNVgqYg5pZW%2Bbn6oJTdc3qlxoiDM02IpUfDaRlzIrknArIVC%2BD%2FKsDSqvJ3KfFISzpFHj%2F1RxnSLYFNz8TkV41QUUwrXMGZuR1e4%2BafLZa8uPkLCB7oSqkAZp8q4iqmBWNBjewYLWnr%2BkzDF1PP9BTq4AlWjLFj%2BLenosc7ppChWVMWh5frBQEw5ZLYEte8XcpYc%2FyQNLp75FjBSsSVmCPcch%2FsxmwnHgB9Q8konl3CZ0nrSqwkA2Z2eSJccsJwr7c7BZScYVd4R8YuhnABIE6OIC%2BpLzc98HI5pkwykp0KMmdUZp1WpMYQPnG8CZTPMOo%2BZLloGhgnQ4TMwj9RybcHQxx5gmfMcQYfEvcIIwInPAOEE825v2BAGzbn6o3fBChmRfyVpbhpj120HZ9Gq8kA5wUV%2F3CZA%2BFTTclYl%2FL8AZ6MCaupcOcEc0E0SA4InELbulD1iIVN0gBq62ui05RuBBoPf4hcTdjl%2BkFayS1GsU%2BvPl%2F7ZKcarA%2FJU200q%2BJd5T8GJA1ds9Sc5kScFZ2Pn23CiknG9i3opoBWi2tfoCiiBds6IOghD%2FQ%3D%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20201124T111926Z&X-Amz-SignedHeaders=host&X-Amz-Expires=300&X-Amz-Credential=ASIARYA2LYGSOCWHGAM6%2F20201124%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Signature=1fcdefe8fa7ff5a42f044956eea0830b7edb97df958a554e1bbabcf020e96e5d";
    try {
      await AssetsAudioPlayer.newPlayer().open(
        Audio.network(url),
      );
    } catch (t) {
      //mp3 unreachable
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: FlatButton(
          color: Colors.black,
          onPressed: () {
            FutureBuilder<UrlToPlay>(
              future: getUrl(),
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  final nurl = snapshot.data;
                  play(nurl.url);
                  return Text("URL : ${nurl.url}");
                } else if (snapshot.hasError) {
                  return Text(snapshot.error.toString());
                }

                return CircularProgressIndicator();
              },
            );

            // String url =
            //     'https://files.freemusicarchive.org/storage-freemusicarchive-org/music/Music_for_Video/springtide/Sounds_strange_weird_but_unmistakably_romantic_Vol1/springtide_-_03_-_We_Are_Heading_to_the_East.mp3';
            // AssetsAudioPlayer.newPlayer().open(
            //   Audio.network(url),
            //   autoStart: true,
            //   showNotification: true,
            // );

            // final snack = SnackBar(
            //   content: Text('FooBar'),
            // );
            //
            // Scaffold.of(context).showSnackBar(snack);
          },
          child: Text('Play', style: TextStyle(color: Colors.amber)),
        ),
      ),
    );
  }
}

Future<UrlToPlay> getUrl() async {
  final url = "localhost:3000/getUrl";
  final response = await http.get(url);

  if (response.statusCode == 200) {
    final jsonUrl = jsonDecode(response.body);
    return UrlToPlay.toJson(jsonUrl);
  } else {
    throw Exception();
  }
}

class UrlToPlay {
  final String url;
  UrlToPlay({this.url});

  factory UrlToPlay.toJson(final json) {
    return UrlToPlay(
      url: json["url"],
    );
  }
}
