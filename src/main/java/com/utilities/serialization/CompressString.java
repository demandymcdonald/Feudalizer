package com.utilities.serialization;

import com.Feudalizer;
import com.Global.*;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressString {

    public static String compress(String input) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(baos)) {
            gzip.write(input.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            Feudalizer.LOGGER.error(e.getMessage());
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
    // Decompress
    public static String decompress(String compressed) {
        byte[] bytes = Base64.getDecoder().decode(compressed);
        try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(bytes));
             InputStreamReader reader = new InputStreamReader(gzip, StandardCharsets.UTF_8);
             BufferedReader buffered = new BufferedReader(reader)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = buffered.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            Feudalizer.LOGGER.error(e.getMessage());
            return null;
        }
    }
}
