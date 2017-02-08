package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(Set(100,200), 100))
  }

  test("singletonSet is implemented"){
    assert(singletonSet(100) === Set(100))
  }




  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s15 = Set(1,2,3,4,5)
    val s37 = Set(3,4,5,6,7)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */

  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }


  test("intersect contains only elements that are in both sets"){
    new TestSets {
      val t = intersect(s15,s37)
      assert(!contains(t,1), "Intersect 1")
      assert(!contains(t,2), "Intersect 2")
      assert(contains(t,3), "Intersect 3")
      assert(contains(t,4), "Intersect 4")
      assert(contains(t,5), "Intersect 5")
      assert(!contains(t,6), "Intersect 6")
      assert(!contains(t,7), "Intersect 7")
      assert(!contains(t,8), "Intersect 8")
    }
  }

  test("diff contains only elements that are not in both sets"){
    new TestSets {
      val t = diff(s15,s37)
      assert(contains(t,1), "Intersect 1")
      assert(contains(t,2), "Intersect 2")
      assert(!contains(t,3), "Intersect 3")
      assert(!contains(t,4), "Intersect 4")
      assert(!contains(t,5), "Intersect 5")
      assert(contains(t,6), "Intersect 6")
      assert(contains(t,7), "Intersect 7")
      assert(!contains(t,8), "Intersect 8")
    }
  }
  test("filter 1-10"){
    val s = Set(1,2,3,4,5,6,7,8,9,10)
    assert(contains(filter(s, x => x % 2 == 0 ),2))
    assert(contains(filter(s, x => x % 2 == 0 ),4))
    assert(contains(filter(s, x => x % 2 == 0 ),6))
    assert(contains(filter(s, x => x % 2 == 0 ),8))
    assert(contains(filter(s, x => x % 2 == 0 ),10))
    assert(!contains(filter(s, x => x % 2 == 0 ),1))
    assert(!contains(filter(s, x => x % 2 == 0 ),3))
    assert(!contains(filter(s, x => x % 2 == 0 ),5))
    assert(!contains(filter(s, x => x % 2 == 0 ),7))
    assert(!contains(filter(s, x => x % 2 == 0 ),9))
  }
  test("forall is correct for small %2 set"){
    val s = Set (2,4,6,8)
    assert(forall(s, x => x % 2 == 0 ), "forall 1")
    assert(!forall(s, x => x % 3 == 0 ), "forall 2")
  }
  test("forall is correct for complete %2 set"){
    val s: Set  = x => x % 2 == 0
    assert(forall(s, x => x % 2 == 0 ), "forall 3")
    assert(!forall(s, x => x % 3 == 0 ), "forall 4")
  }
  test("exists is correct for 1,10 set"){
    val s = Set(1,2,3,4,5,6,7,8,9,10)
    assert(exists(s, x => x % 6 == 0))
    assert(!exists(s, x => x % 11 == 0))
  }
  test("map is correct for small %2 set"){
    val s = Set (2,4,6,8)
    assert(contains(map(s, x => x * x ),16), "map 1")
    assert(!contains(map(s, x => x * x ),2), "map 2")
  }

}
