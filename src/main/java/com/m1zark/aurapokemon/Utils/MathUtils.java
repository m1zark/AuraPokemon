package com.m1zark.aurapokemon.Utils;

import java.util.Random;
import com.flowpowered.math.vector.Vector3d;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

public class MathUtils {
    private static final Random random = new Random(System.nanoTime());

    public static double offset(Location location, Location location2) {
        return MathUtils.offset(location.getPosition(), location2.getPosition());
    }

    private static double offset(Vector3d vector, Vector3d vector2) {
        return vector.sub(vector2).length();
    }

    public static float randomRange(float f, float f2) {
        return f + (float)Math.random() * (f2 - f);
    }

    public static int randomRange(int n, int n2) {
        Random random = new Random();
        int n3 = random.nextInt(n2 - n + 1) + n;
        return n3;
    }

    public static double randomRange(double d, double d2) {
        return Math.random() < 0.5 ? (1.0 - Math.random()) * (d2 - d) + d : Math.random() * (d2 - d) + d;
    }

    public static double arrondi(double d, int n) {
        return (double)((int)(d * Math.pow(10.0, n) + 0.5)) / Math.pow(10.0, n);
    }

    public static /* varargs */ int getRandomWithExclusion(int n, int n2, int ... arrn) {
        int n3 = n + random.nextInt(n2 - n + 1 - arrn.length);
        int[] arrn2 = arrn;
        int n4 = arrn2.length;
        int n5 = 0;
        while (n5 < n4) {
            int n6 = arrn2[n5];
            if (n3 < n6) break;
            ++n3;
            ++n5;
        }
        return n3;
    }

    public static float getLookAtYaw(Vector3d vector) {
        double d = vector.getX();
        double d2 = vector.getZ();
        double d3 = 0.0;
        if (d != 0.0) {
            d3 = d < 0.0 ? 4.71238898038469 : 1.5707963267948966;
            d3 -= Math.atan(d2 / d);
        } else if (d2 < 0.0) {
            d3 = 3.141592653589793;
        }
        return (float)((- d3) * 180.0 / 3.141592653589793 - 90.0);
    }

    public static boolean elapsed(long l, long l2) {
        return System.currentTimeMillis() - l > l2;
    }

    public static Vector3d getBumpVector(Entity entity, Location location, double d) {
        Vector3d vector = entity.getLocation().getPosition().sub(location.getPosition()).normalize();
        vector.mul(d);
        return vector;
    }

    public static Vector3d getPullVector(Entity entity, Location location, double d) {
        Vector3d vector = location.getPosition().sub(entity.getLocation().getPosition()).normalize();
        vector.mul(d);
        return vector;
    }

    public static void bumpEntity(Entity entity, Location location, double d) {
        entity.setVelocity(MathUtils.getBumpVector(entity, location, d));
    }

    public static void bumpEntity(Entity entity, Location location, double d, double d2) {
        if (entity instanceof Player) {
            return;
        }
        Vector3d vector = MathUtils.getBumpVector(entity, location, d);
        vector.add(0.0, d2, 0.0);
        entity.setVelocity(vector);
    }

    public static void pullEntity(Entity entity, Location location, double d) {
        entity.setVelocity(MathUtils.getPullVector(entity, location, d));
    }

    public static void pullEntity(Entity entity, Location location, double d, double d2) {
        Vector3d vector = MathUtils.getPullVector(entity, location, d);
        vector.add(0.0, d2, 0.0);
        entity.setVelocity(vector);
    }

    public static final Vector3d rotateAroundAxisX(Vector3d vector, double d) {
        double d2 = Math.cos(d);
        double d3 = Math.sin(d);
        double d4 = vector.getY() * d2 - vector.getZ() * d3;
        double d5 = vector.getY() * d3 + vector.getZ() * d2;
        return vector.add(0.0, d4, d5);
    }

    public static final Vector3d rotateAroundAxisY(Vector3d vector, double d) {
        double d2 = Math.cos(d);
        double d3 = Math.sin(d);
        double d4 = vector.getX() * d2 + vector.getZ() * d3;
        double d5 = vector.getX() * (- d3) + vector.getZ() * d2;
        return vector.add(d4, 0.0, d5);
    }

    public static final Vector3d rotateAroundAxisZ(Vector3d vector, double d) {
        double d2 = Math.cos(d);
        double d3 = Math.sin(d);
        double d4 = vector.getX() * d2 - vector.getY() * d3;
        double d5 = vector.getX() * d3 + vector.getY() * d2;
        return vector.add(d4, d5, 0.0);
    }

    public static final Vector3d rotateVector(Vector3d vector, double d, double d2, double d3) {
        MathUtils.rotateAroundAxisX(vector, d);
        MathUtils.rotateAroundAxisY(vector, d2);
        MathUtils.rotateAroundAxisZ(vector, d3);
        return vector;
    }

    /*
    public static Vector3d rotate(Vector3d vector, Location location) {
        double d = (double)(location.getYaw() / 180.0f) * 3.141592653589793;
        double d2 = (double)(location.getPitch() / 180.0f) * 3.141592653589793;
        vector = MathUtils.rotateAroundAxisX(vector, d2);
        vector = MathUtils.rotateAroundAxisY(vector, - d);
        return vector;
    }
    */

    public static byte toPackedByte(float f) {
        return (byte)(f * 256.0f / 360.0f);
    }

    public static Vector3d getRandomVector() {
        double d = random.nextDouble() * 2.0 - 1.0;
        double d2 = random.nextDouble() * 2.0 - 1.0;
        double d3 = random.nextDouble() * 2.0 - 1.0;
        return new Vector3d(d, d2, d3).normalize();
    }

    public static Vector3d getRandomCircleVector() {
        double d = random.nextDouble() * 2.0 * 3.141592653589793;
        double d2 = Math.cos(d);
        double d3 = Math.sin(d);
        double d4 = Math.sin(d);
        return new Vector3d(d2, d4, d3);
    }

    public static Vector3d getRandomVectorline() {
        int n = -5;
        int n2 = 5;
        int n3 = (int)(Math.random() * (double)(n2 - n) + (double)n);
        int n4 = (int)(Math.random() * (double)(n2 - n) + (double)n);
        double d = -5.0;
        double d2 = -1.0;
        double d3 = Math.random() * (d2 - d) + d;
        return new Vector3d((double)n4, d3, (double)n3).normalize();
    }

    /*
    public static Location rotate(Location location, Location location2, double d) {
        location.getPosition().add(0.0, location2.getY(), 0.0);
        float f = location2.getYaw() % 360.0f;
        double d2 = location.getPosition().distance(location2.getPosition());
        double d3 = Math.acos((location2.getX() - location.getX()) / d2);
        if (location2.getZ() < location.getZ()) {
            double d4 = 6.283185307179586 - d3 + d * 3.141592653589793 / 180.0;
            double d5 = Math.cos(d4) * d2;
            double d6 = Math.sin(d4) * d2;
            location2 = location.copy().add(d5, 0.0, d6);
            location2.setYaw((float)(((double)f + d) % 360.0));
            return location2;
        }
        double d7 = d3 + d * 3.141592653589793 / 180.0;
        double d8 = Math.cos(d7) * d2;
        double d9 = Math.sin(d7) * d2;
        location2 = location.copy().add(d8, 0.0, d9);
        location2.setYaw((float)(((double)f + d) % 360.0));
        return location2;
    }
    */
}
