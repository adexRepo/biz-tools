import 'package:flutter/material.dart';
import 'package:ui/features/utils/extensions/responsive.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
      child: context.responsive(
        Container(
          color: Colors.black87,
        ),
      ),
    ));
  }
}
