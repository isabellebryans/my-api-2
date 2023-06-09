import rdflib
from rdflib import URIRef, Namespace

g = rdflib.Graph()

ns = Namespace("http://example.org/chess/")

cols = ["a", "b", "c", "d", "e", "f", "g", "h"]

for i in range(16):
    current_piece = URIRef(ns["P" + str(i + 1)])
    if i == 0:  # king
        current_position = URIRef(ns["e1"])
    elif i == 1:  # queen
        current_position = URIRef(ns["d1"])
    elif i == 2:  # 1st bishop
        current_position = URIRef(ns["c1"])
    elif i == 3:  # 2nd bishop
        current_position = URIRef(ns["f1"])
    elif i == 4:  # 1st knight
        current_position = URIRef(ns["b1"])
    elif i == 5:  # 2nd knight
        current_position = URIRef(ns["g1"])
    elif i == 6:  # 1st rook
        current_position = URIRef(ns["a1"])
    elif i == 7:  # 2nd rook
        current_position = URIRef(ns["h1"])
    else:
        current_position = URIRef(ns[cols[i - 8] + "2"])

    g.add((current_piece, ns.isIn, current_position))

for i in range(16):
    current_piece = URIRef(ns["P" + str(i + 17)])
    if i == 0:  # king
        current_position = URIRef(ns["e8"])
    elif i == 1:  # queen
        current_position = URIRef(ns["d8"])
    elif i == 2:  # 1st bishop
        current_position = URIRef(ns["c8"])
    elif i == 3:  # 2nd bishop
        current_position = URIRef(ns["f8"])
    elif i == 4:  # 1st knight
        current_position = URIRef(ns["b8"])
    elif i == 5:  # 2nd knight
        current_position = URIRef(ns["g8"])
    elif i == 6:  # 1st rook
        current_position = URIRef(ns["a8"])
    elif i == 7:  # 2nd rook
        current_position = URIRef(ns["h8"])
    else:
        current_position = URIRef(ns[cols[i - 8] + "7"])

    g.add((current_piece, ns.isIn, current_position))

print(g.serialize(format="turtle"))
g.serialize(destination="../boardStatus.ttl")
g.serialize(destination="initBoardstatus.ttl")
