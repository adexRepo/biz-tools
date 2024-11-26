import 'package:dartz/dartz.dart';
import 'package:ui/core/error/failures.dart';
import 'package:ui/features/authentication/domain/entities/user_entity.dart';
import 'package:ui/features/user_profile/domain/repositories/user_profile_repository.dart';

class GetUserProfileUseCase {
  final UserProfileRepository repository;

  const GetUserProfileUseCase({required this.repository});

  Future<Either<Failure, UserEntity>> call() {
    return repository.getUserProfile();
  }
}
