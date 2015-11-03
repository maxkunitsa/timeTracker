package services;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.mongodb.DuplicateKeyException;
import exceptions.Exceptions;
import exceptions.UserAlreadyExistsException;
import models.User;
import ninja.jongo.annotations.InjectMongoCollection;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Service for users management.
 * <p/>
 * Author: Aleksandr Savvopulo
 * Date: 28.10.2015
 */
@Singleton
public class UserService {
    @InjectMongoCollection(name = "users")
    private MongoCollection users;

    @Inject
    private Exceptions exceptions;

    /**
     * Checks is user with such email already registered.
     *
     * @param email - email to be checked
     * @return true, if email exists, false otherwise
     */
    public boolean isEmailRegistered(String email) {
        return users.count("{email: #}", email) > 0;
    }

    /**
     * Registering new user in the system.
     *
     * @param user user to be registered.
     * @return registered user with id specified.
     * @throws UserAlreadyExistsException in case if user with specified email already exists.
     */
    public User register(User user) throws UserAlreadyExistsException {
        try {
            //Password encoding
            byte[] salt = getSalt(user.getEmail());
            String encodedPassword = encodePassword(user.getPassword(), salt);
            user.setPassword(encodedPassword);

            users.insert(user);

            return user;
        } catch (DuplicateKeyException e) {
            throw exceptions.userAlreadyExists(user);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns user by its credentials.
     *
     * @param email    user's email
     * @param password secret phrase
     * @return User object found by its credentials
     */
    public User authorize(String email, String password) {
        try {
            byte[] salt = getSalt(email);
            String encodedPassword = encodePassword(password, salt);
            String query = "{email: #, password: #}";

            return users.findOne(query, email, encodedPassword).as(User.class);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Returns user by id.
     *
     * @param id user's id
     * @return user object if found, null - otherwise
     */
    public User getById(String id) {
        return users.findOne(new ObjectId(id)).as(User.class);
    }

    /**
     * Encoding password using salt.
     *
     * @param password password to be encoded
     * @param salt     salt
     * @return encoded password
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private String encodePassword(String password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 666, 256);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] encoded = skf.generateSecret(spec).getEncoded();

        String hex = new BigInteger(1, encoded).toString(16);
        int paddingLength = (encoded.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * Generating salt for password encryption algorithm
     * based on string provided.
     *
     * @param string base for salt generation
     * @return salt bytes
     */
    private byte[] getSalt(String string) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(string.getBytes(), 0, string.length());

            return new BigInteger(1, digest.digest()).toString(16).getBytes();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
