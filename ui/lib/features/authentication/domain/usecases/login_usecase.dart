import 'package:dartz/dartz.dart';
import 'package:ui/core/error/failures.dart';
import 'package:ui/features/authentication/domain/entities/credentials_entity.dart';
import 'package:ui/features/authentication/domain/repositories/authentication_repository.dart';

class LogInUseCase {
  final AuthenticationRepository repository;

  LogInUseCase(this.repository);

  Future<Either<Failure, CredentialsEntity>> call() async {
    return await repository.logIn();
  }
}
