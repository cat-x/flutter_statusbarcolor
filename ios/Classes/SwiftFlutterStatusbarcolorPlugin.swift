import Flutter
import UIKit

public class SwiftFlutterStatusbarcolorPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_statusbarcolor", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterStatusbarcolorPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
