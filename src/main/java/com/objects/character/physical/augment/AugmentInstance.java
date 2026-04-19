package com.objects.character.physical.augment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.objects.character.physical.GeneManager;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.text.TextStringBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static java.awt.SystemColor.text;

public record AugmentInstance(Augment augment, int level, StringBuilder additionalDescription, LocalDate start, LocalDate expiration) {
    public JsonElement toJson(){
        StringBuilder builder = new StringBuilder();
        builder.append(Base64.getEncoder().encodeToString(encode(augment.getID())))
                .append(":")
                .append(level)
                .append(":")
                .append(Base64.getEncoder().encodeToString(encode(additionalDescription.toString())))
                .append(":")
                .append(start.toString())
                .append(":")
                .append(expiration.toString());
        return new JsonPrimitive(builder.toString());
    }
    public static AugmentInstance fromJson(JsonElement object){
        String[] split = object.getAsString().split(":");
        Augment au = GeneManager.Augments.getAugment(decode(Base64.getDecoder().decode(split[0])));
        int level = Integer.parseInt(split[1]);
        String additionalDescription = decode(Base64.getDecoder().decode(split[2]));
        LocalDate start = LocalDate.parse(split[3]);
        LocalDate expiration = LocalDate.parse(split[4]);
        return new AugmentInstance(au, level, new StringBuilder(additionalDescription), start, expiration);
    }
    private static byte[] encode(String instance){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
            gzip.write(instance.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return bos.toByteArray();
    }
    private static String decode(byte[] bytes){
        try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(bytes))) {
            return new String(gzip.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        return "";
    }
}
