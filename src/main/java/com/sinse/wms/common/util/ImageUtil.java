package com.sinse.wms.common.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageUtil {
	public static Image getImage(String fileName) {
		URL imageURL = ClassLoader.getSystemResource("images/" + fileName);
		try {
			BufferedImage imageBuffer = ImageIO.read(imageURL);
			return imageBuffer.getScaledInstance(imageBuffer.getWidth(), imageBuffer.getHeight(), Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Image getImage(String fileName, int width, int height) {
		URL imageURL = ClassLoader.getSystemResource("images/" + fileName);
		try {
			BufferedImage imageBuffer = ImageIO.read(imageURL);
			return imageBuffer.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Image getImage(String fileName, int width, int height, boolean crop) {
		URL imageURL = ClassLoader.getSystemResource("images/" + fileName);
		try {
			BufferedImage imageBuffer = ImageIO.read(imageURL);
			if (crop) {
				double ratio = width / height;
				int originalWidth = imageBuffer.getWidth();
				int originalHeight = imageBuffer.getHeight();
				int cropWidth, cropHeight;
				if ((double) originalWidth / originalHeight > ratio) {
					cropHeight = originalHeight;
					cropWidth = (int) (cropHeight * ratio);
				} else {
					cropWidth = originalWidth;
					cropHeight = (int) (cropWidth / ratio);
				}

				int x = (originalWidth - cropWidth) / 2;
				int y = (originalHeight - cropHeight) / 2;
				BufferedImage croppedImage = imageBuffer.getSubimage(x, y, cropWidth, cropHeight);
				return croppedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			}
			return imageBuffer.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
