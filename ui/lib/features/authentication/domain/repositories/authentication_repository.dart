import 'package:dartz/dartz.dart';
import 'package:ui/core/error/failures.dart';
import 'package:ui/features/authentication/domain/entities/credentials_entity.dart';

abstract class AuthenticationRepository {
  Future<Either<Failure, CredentialsEntity>> logIn();
  Future<Either<Failure, Unit>> logOut();
}
