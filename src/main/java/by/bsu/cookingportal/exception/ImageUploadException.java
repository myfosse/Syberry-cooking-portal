package by.bsu.cookingportal.exception;

public class ImageUploadException extends RuntimeException {
  public ImageUploadException(String msg, Throwable t) {
    super(msg, t);
  }

  public ImageUploadException(String msg) {
    super(msg);
  }
}
