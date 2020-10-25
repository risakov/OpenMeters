//
//  StoryboardResolver.swift
//  DITranquillity
//
//  Created by Alexander Ivlev on 05/01/17.
//  Copyright © 2016 Alexander Ivlev. All rights reserved.
//

#if os(iOS) || os(tvOS)
  import UIKit
#elseif os(OSX)
  import Cocoa
#endif
#if os(iOS) || os(tvOS) || os(OSX)

/// The class responsible for injecting dependencies in the view/window controller.
final class StoryboardResolver {
  init(container: DIContainer, bundle: Bundle?) {
    self.container = container
    self.bundle = bundle ?? Bundle.main
  }

  #if os(iOS) || os(tvOS)

  func inject(into viewController: UIViewController) {
    self.container.inject(into: viewController, from: bundle)
    
	for childVC in viewController.children {
      inject(into: childVC)
    }
  }

  #elseif os(OSX)

  func inject(into viewController: Any) {
    self.container.inject(into: viewController, from: bundle)

    if let windowController = viewController as? NSWindowController, let viewController = windowController.contentViewController {
      inject(into: viewController)
    }

    if let nsViewController = viewController as? NSViewController {
      for childVC in nsViewController.children {
        inject(into: childVC)
      }
    }
  }

  #endif

  private let container: DIContainer
  private let bundle: Bundle
}

#endif
