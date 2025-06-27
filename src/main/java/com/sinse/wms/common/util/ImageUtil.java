package com.sinse.wms.common.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.sinse.wms.common.view.ImageView;

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

	public static Image getImage(File file) {
		try {
			BufferedImage imageBuffer = ImageIO.read(file);
			return imageBuffer.getScaledInstance(imageBuffer.getWidth(), imageBuffer.getHeight(), Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Image getImage(File file, int width, int height) {
		try {
			BufferedImage imageBuffer = ImageIO.read(file);
			return imageBuffer.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Image getImageFromBase64(String base64) {
		try {
			BufferedImage imageBuffer = decodeBase64ToImage(base64);
			return imageBuffer.getScaledInstance(imageBuffer.getWidth(), imageBuffer.getHeight(), Image.SCALE_SMOOTH);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Image getImageFromBase64(String base64, int width, int height) {
		try {
			BufferedImage imageBuffer = decodeBase64ToImage(base64);
			return imageBuffer.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (Exception e) {
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

	public static String encodeImageToBase64(ImageView imageView) {
		try {
			Image image = imageView.getImage();
			String extension = imageView.getImageExtension();
			BufferedImage bimage = toBufferedImage(image);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bimage, extension, baos);
			byte[] imageBytes = baos.toByteArray();
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedImage decodeBase64ToImage(String base64String) throws Exception {
		byte[] decodedBytes = Base64.getDecoder().decode(base64String);
		try (ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes)) {
			return ImageIO.read(bais);
		}
	}

	private static BufferedImage toBufferedImage(Image image) {
		BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(image, 0, 0, null);
		bGr.dispose();
		return bimage;
	}
}
