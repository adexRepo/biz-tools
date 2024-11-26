import 'package:dartz/dartz.dart';
import 'package:ui/app/injection_container.dart';
import 'package:ui/core/error/exceptions.dart';
import 'package:ui/core/error/failures.dart';
import 'package:ui/features/authentication/data/datasources/authentication_remote_datasource.dart';
import 'package:ui/features/authentication/domain/entities/credentials_entity.dart';
import 'package:ui/features/authentication/domain/repositories/authentication_repository.dart';
import 'package:hive/hive.dart';

class AuthenticationRepositoryImpl implements AuthenticationRepository {
  final AuthenticationRemoteDataSource authenticationRemoteDataSource;

  AuthenticationRepositoryImpl({required this.authenticationRemoteDataSource});

  @override
  Future<Either<Failure, Unit>> logOut() async {
    try {
      locator<Box>().delete('userMap');
      await authenticationRemoteDataSource.logOut();
      return const Right(unit);
    } catch (e) {
      return Left(ServerFailure(e.toString()));
    }
  }

  @override
  Future<Either<Failure, CredentialsEntity>> logIn() async {
    try {
      final CredentialsEntity credentialsEntity =
          await authenticationRemoteDataSource.logIn();

      // Store as map to simplify Hive storage
      Map<String, dynamic> userMap = {
        'email': credentialsEntity.userEntity.email,
        'name': credentialsEntity.userEntity.name,
        'isEmailVerified': credentialsEntity.userEntity.isEmailVerified,
        'pictureUrl': credentialsEntity.userEntity.pictureUrl.toString(),
        'updatedAt': credentialsEntity.userEntity.updatedAt,
        'nickname': credentialsEntity.userEntity.nickname
      };
      locator<Box>().put('userMap', userMap);

      return Right(credentialsEntity);
    } on ServerException catch (e) {
      if (e.message.contains('verify')) {
        return Left(EmailVerifiedFailure(e.toString()));
      } else if (e.message.contains('timeout')) {
        return Left(TimeoutFailure(e.toString()));
      } else {
        return Left(ServerFailure(e.toString()));
      }
    }
  }
}
