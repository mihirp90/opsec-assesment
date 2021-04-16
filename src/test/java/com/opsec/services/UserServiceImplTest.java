package com.opsec.services;

import com.opsec.exceptions.ResourceNotFoundException;
import com.opsec.model.User;
import com.opsec.repository.UserRepository;
import com.opsec.utils.SequenceGeneratorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserServiceImpl userServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        userServiceImplUnderTest = new UserServiceImpl();
        userServiceImplUnderTest.userRepository = mock(UserRepository.class);
        userServiceImplUnderTest.sequenceGeneratorUtil = mock(SequenceGeneratorUtil.class);
    }

    @Test
    void testCreateUser() {
        // Setup
        final User user = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        final User expectedResult = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        when(userServiceImplUnderTest.sequenceGeneratorUtil.generateSequence("sequence")).thenReturn(0L);

        // Configure UserRepository.save(...).
        final User user1 = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        when(userServiceImplUnderTest.userRepository.save(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"))).thenReturn(user1);

        // Run the test
        final User result = userServiceImplUnderTest.createUser(user);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllUsers() {
        // Setup
        final List<User> expectedResult = Arrays.asList(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));

        // Configure UserRepository.findAll(...).
        final List<User> users = Arrays.asList(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
        when(userServiceImplUnderTest.userRepository.findAll()).thenReturn(users);

        // Run the test
        final List<User> result = userServiceImplUnderTest.getAllUsers();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllUsers_UserRepositoryReturnsNoItems() {
        // Setup
        final List<User> expectedResult = Arrays.asList(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
        when(userServiceImplUnderTest.userRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<User> result = userServiceImplUnderTest.getAllUsers();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetUserById() throws Exception {
        // Setup
        final User expectedResult = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");

        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
        when(userServiceImplUnderTest.userRepository.findById(0L)).thenReturn(user);

        // Run the test
        final User result = userServiceImplUnderTest.getUserById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetUserById_UserRepositoryReturnsAbsent() throws Exception {
        // Setup
        final User expectedResult = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        when(userServiceImplUnderTest.userRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final User result = userServiceImplUnderTest.getUserById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetUserById_ThrowsResourceNotFoundException() {
        // Setup

        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
        when(userServiceImplUnderTest.userRepository.findById(0L)).thenReturn(user);

        // Run the test
        assertThrows(ResourceNotFoundException.class, () -> userServiceImplUnderTest.getUserById(0L));
    }

    @Test
    void testUpdateUser() throws Exception {
        // Setup
        final User user = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        final User expectedResult = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");

        // Configure UserRepository.findById(...).
        final Optional<User> user1 = Optional.of(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
        when(userServiceImplUnderTest.userRepository.findById(0L)).thenReturn(user1);

        // Configure UserRepository.save(...).
        final User user2 = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        when(userServiceImplUnderTest.userRepository.save(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"))).thenReturn(user2);

        // Run the test
        final User result = userServiceImplUnderTest.updateUser(0L, user);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdateUser_UserRepositoryFindByIdReturnsAbsent() throws Exception {
        // Setup
        final User user = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        final User expectedResult = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        when(userServiceImplUnderTest.userRepository.findById(0L)).thenReturn(Optional.empty());

        // Configure UserRepository.save(...).
        final User user1 = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        when(userServiceImplUnderTest.userRepository.save(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"))).thenReturn(user1);

        // Run the test
        final User result = userServiceImplUnderTest.updateUser(0L, user);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdateUser_ThrowsResourceNotFoundException() {
        // Setup
        final User user = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");

        // Configure UserRepository.findById(...).
        final Optional<User> user1 = Optional.of(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
        when(userServiceImplUnderTest.userRepository.findById(0L)).thenReturn(user1);

        // Configure UserRepository.save(...).
        final User user2 = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        when(userServiceImplUnderTest.userRepository.save(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"))).thenReturn(user2);

        // Run the test
        assertThrows(ResourceNotFoundException.class, () -> userServiceImplUnderTest.updateUser(0L, user));
    }

    @Test
    void testDeleteUser() throws Exception {
        // Setup

        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
        when(userServiceImplUnderTest.userRepository.findById(0L)).thenReturn(user);

        // Run the test
        userServiceImplUnderTest.deleteUser(0L);

        // Verify the results
        verify(userServiceImplUnderTest.userRepository).delete(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
    }

    @Test
    void testDeleteUser_UserRepositoryFindByIdReturnsAbsent() throws Exception {
        // Setup
        when(userServiceImplUnderTest.userRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        userServiceImplUnderTest.deleteUser(0L);

        // Verify the results
        verify(userServiceImplUnderTest.userRepository).delete(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
    }

    @Test
    void testDeleteUser_ThrowsResourceNotFoundException() {
        // Setup

        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
        when(userServiceImplUnderTest.userRepository.findById(0L)).thenReturn(user);

        // Run the test
        assertThrows(ResourceNotFoundException.class, () -> userServiceImplUnderTest.deleteUser(0L));
        verify(userServiceImplUnderTest.userRepository).delete(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
    }
}
