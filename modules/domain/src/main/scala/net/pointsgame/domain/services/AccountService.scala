package net.pointsgame.domain.services

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import com.github.nscala_time.time.Imports._
import net.pointsgame.domain.model.User
import net.pointsgame.domain.repositories.UserRepository
import net.pointsgame.domain.{ Constants, DomainException }
import net.pointsgame.domain.helpers.Hasher

final case class AccountService(userRepository: UserRepository) {
  def register(name: String, password: String): Future[Int] = { //TODO: check name characters.
    if (name.isEmpty)
      Future.failed(new DomainException("Name shouldn't be empty."))
    if (name.length > Constants.maxNameLength)
      Future.failed(new DomainException(s"Name length should be not more than ${Constants.maxNameLength}."))
    for {
      exists <- userRepository.existsWithName(name)
      result <- if (exists) {
        Future.failed(new DomainException(s"User with name $name already exists."))
      } else {
        val salt = Hasher.generateSalt(Constants.saltLength)
        userRepository.insert(User(None, name, Hasher.hash(password, salt), salt, DateTime.now()))
      }
    } yield result
  }
}
