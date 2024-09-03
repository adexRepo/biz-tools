import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:http/http.dart';
import 'package:ui/app/app.dart';
import 'package:ui/app/injection_container.dart';
import 'package:http/http.dart' as http;

void main() async {
  await dotenv.load();

  // Future<dynamic> testHttp() async {
  //   try {
  //     final url =
  //         Uri.parse('http://localhost:9091/biz-tools-svc/api/users/test1');
  //     final response = await http.get(url);
  //     print(response.body);
  //     if (response.statusCode == 200) {
  //       return jsonDecode(response.body);
  //     } else {
  //       throw Exception('Request failed with status: ${response.statusCode}.');
  //     }
  //   } on ClientException catch (e) {
  //     // Handle ClientException specifically
  //     print('ClientException: $e');
  //     // Return appropriate error message or handle the error
  //     return {'error': 'ClientException occurred'};
  //   } catch (e) {
  //     print(e);
  //     // Handle other exceptions
  //     return {'error': 'An error occurred'};
  //   }
  // }

  WidgetsFlutterBinding.ensureInitialized();
  await setupServiceLocator();
  // await testHttp();
  runApp(const App());
}
