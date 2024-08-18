import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:ui/app/app.dart';
import 'package:ui/app/injection_container.dart';

void main() async {
  await dotenv.load();
  WidgetsFlutterBinding.ensureInitialized();
  await setupServiceLocator();
  runApp(const App());
}
