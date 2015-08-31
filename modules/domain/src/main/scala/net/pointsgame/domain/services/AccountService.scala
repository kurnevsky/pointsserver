package net.pointsgame.domain.services

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz._
import Scalaz._
import net.pointsgame.domain.model.User
import net.pointsgame.domain.repositories.UserRepository
import net.pointsgame.domain.{ Constants, DomainException }

final case class AccountService(userRepository: UserRepository) {
  def register(name: String): Future[Int] = { //TODO: check name characters.
    if (name.isEmpty)
      Future.failed(new DomainException("Name shouldn't be empty."))
    if (name.length > Constants.maxNameLength)
      Future.failed(new DomainException(s"Name length should be not more than ${Constants.maxNameLength}."))
    val result = for (exists <- userRepository.existsWithName(name)) yield {
      if (exists)
        throw new DomainException(s"User with name $name already exists.")
      userRepository.insert(User(None, name))
    }
    result.join
  }
}
