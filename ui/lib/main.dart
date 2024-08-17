import 'package:flutter/material.dart';

class Main extends StatelessWidget {
  const Main({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      themeMode: ThemeMode.dark,
      title: 'Ecnic Tools',
      debugShowCheckedModeBanner: false,
      home: Center(
        child: ElevatedButton(
          onPressed: () {},
          child: const Text("Login"),
        ),
      ),
    );
  }
}
