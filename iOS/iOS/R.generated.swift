//
// This is a generated file, do not edit!
// Generated by R.swift, see https://github.com/mac-cain13/R.swift
//

import Foundation
import Rswift
import UIKit

/// This `R` struct is generated and contains references to static resources.
struct R: Rswift.Validatable {
  fileprivate static let applicationLocale = hostingBundle.preferredLocalizations.first.flatMap(Locale.init) ?? Locale.current
  fileprivate static let hostingBundle = Bundle(for: R.Class.self)
  
  static func validate() throws {
    try intern.validate()
  }
  
  /// This `R.color` struct is generated, and contains static references to 1 colors.
  struct color {
    /// Color `AccentColor`.
    static let accentColor = Rswift.ColorResource(bundle: R.hostingBundle, name: "AccentColor")
    
    /// `UIColor(named: "AccentColor", bundle: ..., traitCollection: ...)`
    @available(tvOS 11.0, *)
    @available(iOS 11.0, *)
    static func accentColor(compatibleWith traitCollection: UIKit.UITraitCollection? = nil) -> UIKit.UIColor? {
      return UIKit.UIColor(resource: R.color.accentColor, compatibleWith: traitCollection)
    }
    
    fileprivate init() {}
  }
  
  /// This `R.image` struct is generated, and contains static references to 1 images.
  struct image {
    /// Image `openLogo`.
    static let openLogo = Rswift.ImageResource(bundle: R.hostingBundle, name: "openLogo")
    
    /// `UIImage(named: "openLogo", bundle: ..., traitCollection: ...)`
    static func openLogo(compatibleWith traitCollection: UIKit.UITraitCollection? = nil) -> UIKit.UIImage? {
      return UIKit.UIImage(resource: R.image.openLogo, compatibleWith: traitCollection)
    }
    
    fileprivate init() {}
  }
  
  /// This `R.storyboard` struct is generated, and contains static references to 6 storyboards.
  struct storyboard {
    /// Storyboard `Camera`.
    static let camera = _R.storyboard.camera()
    /// Storyboard `History`.
    static let history = _R.storyboard.history()
    /// Storyboard `LaunchScreen`.
    static let launchScreen = _R.storyboard.launchScreen()
    /// Storyboard `Profile`.
    static let profile = _R.storyboard.profile()
    /// Storyboard `Results`.
    static let results = _R.storyboard.results()
    /// Storyboard `Root`.
    static let root = _R.storyboard.root()
    
    /// `UIStoryboard(name: "Camera", bundle: ...)`
    static func camera(_: Void = ()) -> UIKit.UIStoryboard {
      return UIKit.UIStoryboard(resource: R.storyboard.camera)
    }
    
    /// `UIStoryboard(name: "History", bundle: ...)`
    static func history(_: Void = ()) -> UIKit.UIStoryboard {
      return UIKit.UIStoryboard(resource: R.storyboard.history)
    }
    
    /// `UIStoryboard(name: "LaunchScreen", bundle: ...)`
    static func launchScreen(_: Void = ()) -> UIKit.UIStoryboard {
      return UIKit.UIStoryboard(resource: R.storyboard.launchScreen)
    }
    
    /// `UIStoryboard(name: "Profile", bundle: ...)`
    static func profile(_: Void = ()) -> UIKit.UIStoryboard {
      return UIKit.UIStoryboard(resource: R.storyboard.profile)
    }
    
    /// `UIStoryboard(name: "Results", bundle: ...)`
    static func results(_: Void = ()) -> UIKit.UIStoryboard {
      return UIKit.UIStoryboard(resource: R.storyboard.results)
    }
    
    /// `UIStoryboard(name: "Root", bundle: ...)`
    static func root(_: Void = ()) -> UIKit.UIStoryboard {
      return UIKit.UIStoryboard(resource: R.storyboard.root)
    }
    
    fileprivate init() {}
  }
  
  fileprivate struct intern: Rswift.Validatable {
    fileprivate static func validate() throws {
      try _R.validate()
    }
    
    fileprivate init() {}
  }
  
  fileprivate class Class {}
  
  fileprivate init() {}
}

struct _R: Rswift.Validatable {
  static func validate() throws {
    try storyboard.validate()
  }
  
  struct storyboard: Rswift.Validatable {
    static func validate() throws {
      try camera.validate()
      try history.validate()
      try launchScreen.validate()
      try profile.validate()
      try results.validate()
      try root.validate()
    }
    
    struct camera: Rswift.StoryboardResourceWithInitialControllerType, Rswift.Validatable {
      typealias InitialController = CameraViewController
      
      let bundle = R.hostingBundle
      let cameraVC = StoryboardViewControllerResource<CameraViewController>(identifier: "cameraVC")
      let name = "Camera"
      
      func cameraVC(_: Void = ()) -> CameraViewController? {
        return UIKit.UIStoryboard(resource: self).instantiateViewController(withResource: cameraVC)
      }
      
      static func validate() throws {
        if #available(iOS 11.0, *) {
        }
        if _R.storyboard.camera().cameraVC() == nil { throw Rswift.ValidationError(description:"[R.swift] ViewController with identifier 'cameraVC' could not be loaded from storyboard 'Camera' as 'CameraViewController'.") }
      }
      
      fileprivate init() {}
    }
    
    struct history: Rswift.StoryboardResourceWithInitialControllerType, Rswift.Validatable {
      typealias InitialController = HistoryViewController
      
      let bundle = R.hostingBundle
      let historyVC = StoryboardViewControllerResource<HistoryViewController>(identifier: "historyVC")
      let name = "History"
      
      func historyVC(_: Void = ()) -> HistoryViewController? {
        return UIKit.UIStoryboard(resource: self).instantiateViewController(withResource: historyVC)
      }
      
      static func validate() throws {
        if #available(iOS 11.0, *) {
        }
        if _R.storyboard.history().historyVC() == nil { throw Rswift.ValidationError(description:"[R.swift] ViewController with identifier 'historyVC' could not be loaded from storyboard 'History' as 'HistoryViewController'.") }
      }
      
      fileprivate init() {}
    }
    
    struct launchScreen: Rswift.StoryboardResourceWithInitialControllerType, Rswift.Validatable {
      typealias InitialController = UIKit.UIViewController
      
      let bundle = R.hostingBundle
      let name = "LaunchScreen"
      
      static func validate() throws {
        if UIKit.UIImage(named: "openLogo", in: R.hostingBundle, compatibleWith: nil) == nil { throw Rswift.ValidationError(description: "[R.swift] Image named 'openLogo' is used in storyboard 'LaunchScreen', but couldn't be loaded.") }
        if #available(iOS 11.0, *) {
        }
      }
      
      fileprivate init() {}
    }
    
    struct profile: Rswift.StoryboardResourceWithInitialControllerType, Rswift.Validatable {
      typealias InitialController = ProfileViewController
      
      let bundle = R.hostingBundle
      let name = "Profile"
      let profileVC = StoryboardViewControllerResource<ProfileViewController>(identifier: "profileVC")
      
      func profileVC(_: Void = ()) -> ProfileViewController? {
        return UIKit.UIStoryboard(resource: self).instantiateViewController(withResource: profileVC)
      }
      
      static func validate() throws {
        if #available(iOS 11.0, *) {
        }
        if _R.storyboard.profile().profileVC() == nil { throw Rswift.ValidationError(description:"[R.swift] ViewController with identifier 'profileVC' could not be loaded from storyboard 'Profile' as 'ProfileViewController'.") }
      }
      
      fileprivate init() {}
    }
    
    struct results: Rswift.StoryboardResourceType, Rswift.Validatable {
      let bundle = R.hostingBundle
      let name = "Results"
      
      static func validate() throws {
        if #available(iOS 11.0, *) {
        }
      }
      
      fileprivate init() {}
    }
    
    struct root: Rswift.StoryboardResourceWithInitialControllerType, Rswift.Validatable {
      typealias InitialController = RootViewController
      
      let bundle = R.hostingBundle
      let name = "Root"
      let rootVC = StoryboardViewControllerResource<RootViewController>(identifier: "rootVC")
      
      func rootVC(_: Void = ()) -> RootViewController? {
        return UIKit.UIStoryboard(resource: self).instantiateViewController(withResource: rootVC)
      }
      
      static func validate() throws {
        if UIKit.UIImage(named: "light_icon_back", in: R.hostingBundle, compatibleWith: nil) == nil { throw Rswift.ValidationError(description: "[R.swift] Image named 'light_icon_back' is used in storyboard 'Root', but couldn't be loaded.") }
        if UIKit.UIImage(named: "tab_bar_sto", in: R.hostingBundle, compatibleWith: nil) == nil { throw Rswift.ValidationError(description: "[R.swift] Image named 'tab_bar_sto' is used in storyboard 'Root', but couldn't be loaded.") }
        if UIKit.UIImage(named: "tab_bar_strar", in: R.hostingBundle, compatibleWith: nil) == nil { throw Rswift.ValidationError(description: "[R.swift] Image named 'tab_bar_strar' is used in storyboard 'Root', but couldn't be loaded.") }
        if UIKit.UIImage(named: "tab_bar_video", in: R.hostingBundle, compatibleWith: nil) == nil { throw Rswift.ValidationError(description: "[R.swift] Image named 'tab_bar_video' is used in storyboard 'Root', but couldn't be loaded.") }
        if #available(iOS 11.0, *) {
          if UIKit.UIColor(named: "purple", in: R.hostingBundle, compatibleWith: nil) == nil { throw Rswift.ValidationError(description: "[R.swift] Color named 'purple' is used in storyboard 'Root', but couldn't be loaded.") }
        }
        if _R.storyboard.root().rootVC() == nil { throw Rswift.ValidationError(description:"[R.swift] ViewController with identifier 'rootVC' could not be loaded from storyboard 'Root' as 'RootViewController'.") }
      }
      
      fileprivate init() {}
    }
    
    fileprivate init() {}
  }
  
  fileprivate init() {}
}
