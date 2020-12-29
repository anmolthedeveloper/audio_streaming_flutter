import 'dart:async';
import 'dart:convert';

import 'package:audio_streaming/round_icon_button.dart';
import 'package:audioplayers/audioplayers.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'config.dart';

class PlayerUI extends StatefulWidget {
  @override
  _PlayerState createState() => _PlayerState();
}

enum PlayerState { stopped, playing, paused }

class _PlayerState extends State<PlayerUI> {
  Duration duration, position;
  AudioPlayer player;
  String text = "Pause";
  bool isPlaying = true;
  String url;

  double sliderValue = 0.0;

  @override
  void initState() {
    super.initState();
    initAudioPlayer();
  }

  @override
  void dispose() {
    player.stop();
    super.dispose();
  }

  void initAudioPlayer() {
    player = AudioPlayer();
    player.onAudioPositionChanged.listen((p) => setState(() => position = p));
    player.onDurationChanged.listen((s) => setState(() => duration = s));
  }

  play(String url) async {
    if (CURRENT_RUNNABLE_MODE == RunnableMODE.emulator &&
        CURRENT_DEV_MODE == DevMODE.testing)
      url = "http://10.0.2.2:3000/$url"; //for emulator
    else if (CURRENT_RUNNABLE_MODE == RunnableMODE.realDevice &&
        CURRENT_DEV_MODE == DevMODE.testing)
      url =
          "http://192.168.2.5:3000/$url"; // my mobile phone that is This PC's IP
    //print(url);
    try {
      await player.play(url);
    } catch (t) {
      //mp3 unreachable
      print(t.toString());
    }
  }

  pause() async {
    await player.pause();
  }

  resume() async {
    await player.resume();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Song name goes here'),
      ),
      body: Container(
        child: FutureBuilder<UrlToPlay>(
          future: getUrl(),
          builder: (context, snapshot) {
            if (snapshot.hasData) {
              final nurl = snapshot.data;
              url = nurl.url;
              //print("++++++++++++++++++++++++++++++++++++++++++++++++ " + nurl.url);

              //
              //               // while (player.isPlaying()) { if we put it in a loop it will hold the UI thread!!!
              //   setState(() {
              //     pos = songPos.inSeconds.round();
              //     print("VALUE=====" + pos.toString());
              //   });
              // }
              return Container(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    Container(
                      color: Colors.blueGrey,
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        children: <Widget>[
                          RoundIconButton(
                            icon: Icons.replay_10,
                            onPressed: () {
                              player.seek(Duration(
                                  milliseconds:
                                      position.inMilliseconds - 10000));
                            },
                          ),
                          Container(
                            child: RaisedButton(
                              child: Text(text),
                              onPressed: () {
                                setState(() {
                                  if (isPlaying) {
                                    pause();
                                    text = "Play";
                                    isPlaying = false;
                                  } else {
                                    resume();
                                    text = "Pause";
                                    isPlaying = true;
                                  }
                                });
                              },
                            ),
                          ),
                          RoundIconButton(
                            icon: Icons.forward_10,
                            onPressed: () {
                              player.seek(Duration(
                                  milliseconds:
                                      position.inMilliseconds + 10000));
                            },
                          ),
                        ],
                      ),
                    ),
                    Container(
                      color: Colors.blueGrey,
                      child: SliderTheme(
                        data: SliderTheme.of(context).copyWith(
                          thumbColor: Colors.amber,
                          activeTrackColor: Colors.blue,
                          overlayColor: Color(0x29EB1555),
                          // thumbShape:
                          //     RoundSliderThumbShape(enabledThumbRadius: 15.0),
                          // overlayShape:
                          //     RoundSliderOverlayShape(overlayRadius: 30.0),
                        ),
                        child: Slider(
                            value: position?.inMilliseconds?.toDouble() ?? 0.0,
                            min: 0.0,
                            max: duration?.inMilliseconds?.toDouble() ?? 100.0,
                            onChanged: (double value) {
                              setState(() {
                                return player.seek(
                                    Duration(milliseconds: value.round()));
                              });
                            }),
                      ),
                    ),
                  ],
                ),
              );
            } else if (snapshot.hasError) {
              return Text(snapshot.error.toString());
            }
            return CircularProgressIndicator();
          },
        ),
      ),
    );
  }
}

Future<UrlToPlay> getUrl() async {
  var url;
  var api = CURRENT_DEV_MODE == DevMODE.server ? "getUrl" : "getTestUrl";
  if (CURRENT_RUNNABLE_MODE == RunnableMODE.emulator)
    url = "http://10.0.2.2:3000/$api"; //for emulator
  else
    url =
        "http://192.168.2.5:3000/$api"; // my mobile phone this is This PC's IP
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
