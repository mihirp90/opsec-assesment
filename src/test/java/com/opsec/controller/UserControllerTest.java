package com.opsec.controller;

import com.opsec.exceptions.ResourceNotFoundException;
import com.opsec.model.User;
import com.opsec.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserController userControllerUnderTest;

    @BeforeEach
    void setUp() {
        userControllerUnderTest = new UserController();
        userControllerUnderTest.userService = mock(UserService.class);
    }

    @Test
    void testCreateUser() {
        // Setup
        final User user = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        final User expectedResult = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");

        // Configure UserService.createUser(...).
        final User user1 = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        when(userControllerUnderTest.userService.createUser(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"))).thenReturn(user1);

        // Run the test
        final User result = userControllerUnderTest.createUser(user);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllUsers() {
        // Setup
        final List<User> expectedResult = Arrays.asList(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));

        // Configure UserService.getAllUsers(...).
        final List<User> users = Arrays.asList(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
        when(userControllerUnderTest.userService.getAllUsers()).thenReturn(users);

        // Run the test
        final List<User> result = userControllerUnderTest.getAllUsers();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllUsers_UserServiceReturnsNoItems() {
        // Setup
        final List<User> expectedResult = Arrays.asList(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"));
        when(userControllerUnderTest.userService.getAllUsers()).thenReturn(Collections.emptyList());

        // Run the test
        final List<User> result = userControllerUnderTest.getAllUsers();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetUserById() throws Exception {
        // Setup
        final ResponseEntity<User> expectedResult = new ResponseEntity<>(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"), HttpStatus.OK);

        // Configure UserService.getUserById(...).
        final User user = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        when(userControllerUnderTest.userService.getUserById(0L)).thenReturn(user);

        // Run the test
        final ResponseEntity<User> result = userControllerUnderTest.getUserById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetUserById_UserServiceThrowsResourceNotFoundException() throws Exception {
        // Setup
        when(userControllerUnderTest.userService.getUserById(0L)).thenThrow(ResourceNotFoundException.class);

        // Run the test
        assertThrows(ResourceNotFoundException.class, () -> userControllerUnderTest.getUserById(0L));
    }

    @Test
    void testUpdateUser() throws Exception {
        // Setup
        final User userDetails = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        final ResponseEntity<User> expectedResult = new ResponseEntity<>(new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"), HttpStatus.OK);

        // Configure UserService.updateUser(...).
        final User user = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        when(userControllerUnderTest.userService.updateUser(0L, new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"))).thenReturn(user);

        // Run the test
        final ResponseEntity<User> result = userControllerUnderTest.updateUser(0L, userDetails);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testUpdateUser_UserServiceThrowsResourceNotFoundException() throws Exception {
        // Setup
        final User userDetails = new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title");
        when(userControllerUnderTest.userService.updateUser(0L, new User(0L, "firstName", "surName", new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "title"))).thenThrow(ResourceNotFoundException.class);

        // Run the test
        assertThrows(ResourceNotFoundException.class, () -> userControllerUnderTest.updateUser(0L, userDetails));
    }

    @Test
    void testDeleteUser() throws Exception {
        // Setup
        final Map<String, Boolean> expectedResult = new HashMap<>();

        // Run the test
        final Map<String, Boolean> result = userControllerUnderTest.deleteUser(0L);

        // Verify the results
        assertEquals(expectedResult, result);
        verify(userControllerUnderTest.userService).deleteUser(0L);
    }

    @Test
    void testDeleteUser_UserServiceThrowsResourceNotFoundException() throws Exception {
        // Setup
        doThrow(ResourceNotFoundException.class).when(userControllerUnderTest.userService).deleteUser(0L);

        // Run the test
        assertThrows(ResourceNotFoundException.class, () -> userControllerUnderTest.deleteUser(0L));
    }
}
