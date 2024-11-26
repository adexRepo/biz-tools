import 'package:dartz/dartz.dart';
import 'package:ui/core/error/failures.dart';
import 'package:ui/features/authentication/domain/entities/user_entity.dart';

abstract class UserProfileRepository {
  Future<Either<Failure, UserEntity>> getUserProfile();
}
