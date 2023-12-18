import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(const App());

class App extends StatelessWidget {
  const App({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: MyApp(),
    );
  }
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String myname = "mubashir";
  static const platform = MethodChannel('flutternativecode');
  String _message = '';
  int battery = 0;
  var location;
  TextEditingController _controllerNama = TextEditingController();

  @override
  void initState() {
    _message = 'Hello Khastech, from Flutter code';
    _controllerNama = TextEditingController();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Flutter to Native Code'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.symmetric(
                horizontal: 16.0,
                vertical: 8.0,
              ),
              child: TextField(
                controller: _controllerNama,
                decoration: const InputDecoration(hintText: "Input your name"),
              ),
            ),
            ElevatedButton(
              child: const Text('Arguments'),
              onPressed: () async {
                try {
                  String name = _controllerNama.text;
                  _message = await platform.invokeMethod(
                    'greetingFromNativeCode',
                    {'name': name},
                  );
                } on PlatformException catch (e) {
                  _message = 'Failed to invoke: ${e.message}';
                }
                setState(() {});
              },
            ),
            ElevatedButton(
              child: const Text('Show Toast'),
              onPressed: () async {
                try {
                  _message = await platform.invokeMethod(
                    'showToast',
                    {'message': "Hello Khastech"},
                  );
                } on PlatformException catch (e) {
                  _message = 'Failed to invoke: ${e.message}';
                }
                setState(() {});
              },
            ),
            // ElevatedButton(
            //   child: const Text('Location'),
            //   onPressed: () async {
            //     try {
            //       location = await platform.invokeMethod(
            //         'getLocation',
            //       );
            //     } on PlatformException catch (e) {
            //       _message = 'Failed to invoke: ${e.message}';
            //     }
            //     setState(() {});
            //   },
            // ),
            ElevatedButton(
              child: const Text('Get battery Level'),
              onPressed: () async {
                try {
                  battery = await platform.invokeMethod(
                    'getBatteryLevel',
                  );
                } on PlatformException catch (e) {
                  _message = 'Failed to invoke: ${e.message}';
                }
                setState(() {});
              },
            ),
            Text(_message),
            location == null ? const SizedBox() : Text(location.toString()),
            battery != 0 ? Text(battery.toString()) : const SizedBox(),
          ],
        ),
      ),
    );
  }
}
