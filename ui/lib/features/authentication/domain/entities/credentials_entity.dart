import 'package:equatable/equatable.dart';
import 'package:ui/features/authentication/domain/entities/user_entity.dart';

  class CredentialsEntity extends Equatable {
    final String idToken;
    final String accessToken;
    final UserEntity userEntity;

    const CredentialsEntity(
        {required this.idToken,
        required this.accessToken,
        required this.userEntity});

    @override
    List<Object?> get props {
      return [idToken, accessToken, userEntity];
    }
  }
