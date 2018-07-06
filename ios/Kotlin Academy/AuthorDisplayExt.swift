//
//  AuthorDisplayExt.swift
//  Kotlin Academy
//
//  Created by Marcin Moskala on 06.07.2018.
//  Copyright © 2018 Dhakad Technosoft. All rights reserved.
//

import Foundation
import UIKit
import SDWebImage
import SafariServices

extension UITableViewCell {
    
    func displayAuthor(_ authBtn: UIButton, _ authLbl: UILabel, _ name: String?, _ url: String?) {
        authBtn.isHidden = url == nil
        authLbl.isHidden = name == nil
        if let name = name {
            if url != nil && url != "" {
                let textRange = NSRange(location: 0, length: name.count)
                let attributedText = NSMutableAttributedString(string: name)
                attributedText.addAttribute(NSAttributedStringKey.foregroundColor, value: UIColor(red: 244.0/255.0, green: 120.0/255.0, blue: 39.0/255.0, alpha: 1.0) , range: textRange)
                attributedText.addAttribute(NSAttributedStringKey.underlineStyle, value: NSUnderlineStyle.styleSingle.rawValue, range: textRange)
                
                authBtn.setAttributedTitle(attributedText, for: .normal)
            } else {
                authBtn.setTitle(name, for: .normal)
            }
        }
    }
}
