// import 'package:audioplayers/audio_cache.dart';
// import 'package:audioplayers/audioplayers.dart';
//
// import 'config.dart';
//
// class AudioPlayers {
//   AudioPlayer player;
//   AudioCache audioCache;
//   Stream<Duration> _duration;
//   Stream<Duration> _position;
//
//   void initPlayer() {
//     player = new AudioPlayer();
//     audioCache = new AudioCache(fixedPlayer: player);
//     _duration = player.onDurationChanged;
//     _position = player.onAudioPositionChanged;
//   }
//
//   play(String url) async {
//     if (CURRENT_RUNNABLE_MODE == RunnableMODE.emulator &&
//         CURRENT_DEV_MODE == DevMODE.testing)
//       url = "http://10.0.2.2:3000/$url"; //for emulator
//     else if (CURRENT_RUNNABLE_MODE == RunnableMODE.realDevice &&
//         CURRENT_DEV_MODE == DevMODE.testing)
//       url =
//           "http://192.168.2.5:3000/$url"; // my mobile phone that is This PC's IP
//
//     print(url);
//     try {
//       await player.play(url);
//     } catch (t) {
//       //mp3 unreachable
//       print(t.toString());
//     }
//   }
//
//   forward() async {
//     await player.seek(Duration(seconds: 10));
//   }
//
//   backward() async {
//     await player.seek(Duration(seconds: -10));
//   }
//
//   bool isPlaying() {
//     //player.;
//   }
// }
