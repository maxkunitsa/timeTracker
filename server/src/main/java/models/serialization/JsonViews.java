package models.serialization;

/**
 * Contains JsonView views to define what
 * fields should be serialized in what cases.
 *
 * Author: Aleksandr Savvopulo
 * Date: 31.10.2015
 */
public class JsonViews {
    /**
     * Fields should be available on client side for any role.
     */
    public static class Public{}

    /**
     * Fields shouldn't be rendered to client side at all.
     */
    public static class Private{}
}
